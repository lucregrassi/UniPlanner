package com.lucreziagrassi.androidapp.futureExams;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lucreziagrassi.androidapp.db.Subject;
import com.lucreziagrassi.androidapp.generic.DatePickerFragment;
import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.FutureExam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FutureExamFragment extends Fragment implements View.OnClickListener {

    private FutureExam currentFutureExam;

    public FutureExamFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        CardView addFutureExamButton = (CardView) getView().findViewById(R.id.addFutureExam);
        addFutureExamButton.setOnClickListener(this);
        EditText exam_date = (EditText) getView().findViewById(R.id.exam_date);
        exam_date.setOnClickListener(this);

        List<String> subjects = new ArrayList<>();
        subjects.add("Seleziona una materia");

        for(Subject subject : DatabaseManager.getDatabase().getSubjectDao().getAll())
            subjects.add(subject.getSubject());

        final String[] subjectsArray = subjects.toArray(new String[0]);

        Spinner spinner = (Spinner) getView().findViewById(R.id.subjects_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, subjectsArray) {
            @Override
            public boolean isEnabled(int position) {
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    tv.setTextColor(Color.GRAY);
                } else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // Set modifying subject if present
        if(this.getArguments() != null) {
            Integer currentFutureExamID = (Integer)this.getArguments().get("currentFutureExam");

            if(currentFutureExamID != null)
                this.currentFutureExam = DatabaseManager.getDatabase().getFutureExamDao().get(currentFutureExamID);
        }

        if(this.currentFutureExam != null) {
            Spinner spinner2 = getView().findViewById(R.id.subjects_spinner);

            for(int i = 0; i < spinner2.getCount(); i++)
            {
                String item = (String)spinner2.getItemAtPosition(i);

                if(item.equals(currentFutureExam.getSubject()))
                    spinner2.setSelection(i);
            }

            DateFormat df = new SimpleDateFormat("dd/MM/yy", Locale.ITALY);
            ((EditText) getView().findViewById(R.id.exam_date)).setText(df.format(new Date(currentFutureExam.getDate())));

            ((TextView)getView().findViewById(R.id.addFutureExamText)).setText(R.string.modify_button);
            getActivity().setTitle(R.string.new_future_exam_modify_fragment_name);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.future_exam, container, false);
        getActivity().setTitle(R.string.new_future_exam_fragment_name);
        return view;
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.add_button);
        item.setVisible(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_home_drawer, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void onAddFutureExamClick() {
        // Prendo le stringhe dei textView
        String subject = ((Spinner) getView().findViewById(R.id.subjects_spinner)).getSelectedItem().toString();
        String date = ((EditText) getView().findViewById(R.id.exam_date)).getText().toString();
        Integer cfu = 0;

        for(Subject subj :DatabaseManager.getDatabase().getSubjectDao().getAll()) {
            if (subj.getSubject().equals(subject))
                cfu = subj.getCfu();
        }

        if (!subject.equals("Seleziona una materia") && !date.isEmpty()) {
            // Date to timestamp
            Long timestamp = 0L;

            try {
                DateFormat df = new SimpleDateFormat("dd/MM/yy", Locale.ITALY);
                Date result = df.parse(date);

                timestamp = result.getTime();
            }
            catch(ParseException ignored) { }

            if(timestamp < Calendar.getInstance().getTimeInMillis() - 60 * 60 * 24 * 1000) {
                Toast.makeText(getActivity(), "Non puoi retrodatare un esame futuro", Toast.LENGTH_SHORT).show();
                return;
            }

            if(this.currentFutureExam != null)
                DatabaseManager.getDatabase().getFutureExamDao().delete(currentFutureExam);

            // Se i dati sono validi, creo l'esame
            FutureExam newFutureExam = new FutureExam(0, subject, timestamp, cfu);
            DatabaseManager.getDatabase().getFutureExamDao().insert(newFutureExam);

            Toast.makeText(getActivity(), "Esame aggiunto con successo!", Toast.LENGTH_SHORT).show();

            // Chiude la tastiera
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocusedView = getActivity().getCurrentFocus();

            if (currentFocusedView != null)
                inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            // Ritorna al libretto
            getFragmentManager().popBackStack();
        } else Toast.makeText(getActivity(), "Riempi tutti i campi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addFutureExam:
                onAddFutureExamClick();
                break;

            case R.id.exam_date:
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getFragmentManager(), "Date Picker");
                break;
        }
    }
}
