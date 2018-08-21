package com.lucreziagrassi.androidapp.timetable;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.Lesson;
import com.lucreziagrassi.androidapp.db.PassedExam;
import com.lucreziagrassi.androidapp.db.Subject;
import com.lucreziagrassi.androidapp.generic.TimePickerFragment;

import java.util.ArrayList;
import java.util.List;

public class NewLessonFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        CardView addNewLessonButton = (CardView) getView().findViewById(R.id.addNewLesson);
        addNewLessonButton.setOnClickListener(this);
        EditText start_hour = (EditText) getView().findViewById(R.id.start_hour);
        start_hour.setOnClickListener(this);
        EditText end_hour = (EditText) getView().findViewById(R.id.end_hour);
        end_hour.setOnClickListener(this);

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
        spinner.setOnItemSelectedListener(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.new_lesson, container, false);
        getActivity().setTitle(R.string.new_lesson_fragment_name);
        return view;
    }

    public void onAddNewLessonClick() {
        // Prendo le stringhe dei textView
        String subjectName = (String)((Spinner) getView().findViewById(R.id.subjects_spinner)).getSelectedItem();

        Subject subject = null;

        for(Subject sub : DatabaseManager.getDatabase().getSubjectDao().getAll())
        {
            if(sub.getSubject().equals(subjectName))
                subject = sub;
        }

        if(subject == null)
        {
            Toast.makeText(getActivity(), "Seleziona una materia", Toast.LENGTH_SHORT).show();
            return;
        }

        String professor = subject.getProfessor();

        String location = ((EditText) getView().findViewById(R.id.location)).getText().toString();
        Integer color = subject.getColor();
        String startHour = ((EditText) getView().findViewById(R.id.start_hour)).getText().toString();
        String endHour = ((EditText) getView().findViewById(R.id.end_hour)).getText().toString();
        Integer day = getArguments().getInt("selectedDay");

        if (!subject.equals("Seleziona una materia") && !professor.isEmpty() &&!location.isEmpty() && !startHour.isEmpty() && !endHour.isEmpty()) {
            // Se i dati sono validi, creo l'esame
            Lesson newLesson = new Lesson(0, subjectName, professor, location, color, startHour, endHour, day);
            DatabaseManager.getDatabase().getLessonDao().insert(newLesson);
            Toast.makeText(getActivity(), "Lezione aggiunta con successo!", Toast.LENGTH_SHORT).show();

            // Chiude la tastiera
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocusedView = getActivity().getCurrentFocus();
            if (currentFocusedView != null)
                inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            // Ritorna indietro
            getFragmentManager().popBackStack();
        } else Toast.makeText(getActivity(), "Riempi tutti i campi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.start_hour:
            case R.id.end_hour:
                DialogFragment timePicker = new TimePickerFragment();

                // Imposto su quale EditText devo inserire l'ora
                Bundle timePickerArguments = new Bundle();
                timePickerArguments.putInt("timeEditTextID", v.getId());
                timePicker.setArguments(timePickerArguments);
                timePicker.show(getFragmentManager(), "Time Picker");
                break;

            case R.id.addNewLesson:
                onAddNewLessonClick();
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String subject = adapterView.getItemAtPosition(position).toString();
        if (position > 0)
            Toast.makeText(adapterView.getContext(), "Hai selezionato: " + subject, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
