package com.lucreziagrassi.androidapp.booklet;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import com.lucreziagrassi.androidapp.db.PassedExam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PassedExamFragment extends Fragment implements View.OnClickListener {

    private PassedExam currentPassedExam;

    public PassedExamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        CardView addPassedExamButton = (CardView) getView().findViewById(R.id.addPassedExam);
        addPassedExamButton.setOnClickListener(this);
        EditText exam_date = (EditText) getView().findViewById(R.id.exam_date);
        exam_date.setOnClickListener(this);

        List<String> subjects = new ArrayList<>();
        subjects.add("Seleziona una materia");

        for(Subject subject : DatabaseManager.getDatabase().getSubjectDao().getAll())
            subjects.add(subject.getSubject());

        final String[] subjectsArray = subjects.toArray(new String[0]);

        Spinner spinner = (Spinner) getView().findViewById(R.id.subjects_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1, subjectsArray){
                @Override
                public boolean isEnabled(int position) {
                    return position != 0;
                }

                @Override
                public View getDropDownView(int position, View convertView,
                                            @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0) {
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
            Integer currentPassedExamID = (Integer)this.getArguments().get("currentPassedExam");

            if(currentPassedExamID != null)
                this.currentPassedExam = DatabaseManager.getDatabase().getPassedExamDao().get(currentPassedExamID);
        }

        if(this.currentPassedExam != null) {
            Spinner spinner2 = getView().findViewById(R.id.subjects_spinner);

            for(int i = 0; i < spinner2.getCount(); i++)
            {
                String item = (String)spinner2.getItemAtPosition(i);

                if(item.equals(currentPassedExam.getSubject()))
                    spinner2.setSelection(i);
            }

            ((EditText) getView().findViewById(R.id.exam_vote)).setText(currentPassedExam.getVote() + "");
            ((EditText) getView().findViewById(R.id.exam_date)).setText(currentPassedExam.getDate());

            ((TextView)getView().findViewById(R.id.addPassedExamText)).setText(R.string.modify_button);
            getActivity().setTitle(R.string.new_exam_modify_fragment_name);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.passed_exam, container, false);
        getActivity().setTitle(R.string.new_exam_fragment_name);
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

    public void onAddPassedExamClick() {
        // Prendo le stringhe dei textView
        String subject = ((Spinner) getView().findViewById(R.id.subjects_spinner)).getSelectedItem().toString();
        String vote = ((EditText) getView().findViewById(R.id.exam_vote)).getText().toString();
        String date = ((EditText) getView().findViewById(R.id.exam_date)).getText().toString();
        Integer cfu = 0;

        for(Subject subj :DatabaseManager.getDatabase().getSubjectDao().getAll()) {
            if (subj.getSubject().equals(subject))
                cfu = subj.getCfu();
        }

        if (!subject.equals("Seleziona una materia") && !date.isEmpty() && !vote.isEmpty()) {
            Integer intVoto = Integer.parseInt(vote);
            if (intVoto > 17 && intVoto < 31) {
                // Cancello un esame da modificare
                if(this.currentPassedExam != null)
                    DatabaseManager.getDatabase().getPassedExamDao().delete(currentPassedExam);

                Long timestamp = 0L;

                // Check data
                try {
                    DateFormat df = new SimpleDateFormat("dd/MM/yy", Locale.ITALY);
                    Date result = df.parse(date);

                    timestamp = result.getTime();
                }
                catch(ParseException ignored) { }

                if(timestamp > Calendar.getInstance().getTimeInMillis())
                {
                    Toast.makeText(getActivity(), "Non puoi inserire una data futura", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Se i dati sono validi, creo l'esame
                PassedExam newPassedExam = new PassedExam(0, subject, intVoto, date, cfu);

                // Check che il nome sia univoco
                for(PassedExam pe : DatabaseManager.getDatabase().getPassedExamDao().getAll()) {
                    if (pe.getSubject().equals(newPassedExam.getSubject())) {
                        // Nome già usato
                        Toast.makeText(getActivity(), "Hai già inserito questo esame", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                DatabaseManager.getDatabase().getPassedExamDao().insert(newPassedExam);
                Toast.makeText(getActivity(), "Esame aggiunto con successo!", Toast.LENGTH_SHORT).show();

                // Chiude la tastiera
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                View currentFocusedView = getActivity().getCurrentFocus();
                if (currentFocusedView != null)
                    inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                // Ritorna al libretto
                getFragmentManager().popBackStack();
            }
            else
                Toast.makeText(getActivity(), "Inserisci un voto tra 18 e 30", Toast.LENGTH_SHORT).show();

        }
        else
            Toast.makeText(getActivity(), "Riempi tutti i campi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addPassedExam:
                onAddPassedExamClick();
                break;

            case R.id.exam_date:
                android.support.v4.app.DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getFragmentManager(), "Date Picker");
                break;
        }
    }
}
