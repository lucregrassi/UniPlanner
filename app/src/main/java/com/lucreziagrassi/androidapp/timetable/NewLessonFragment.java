package com.lucreziagrassi.androidapp.timetable;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.Lesson;
import com.lucreziagrassi.androidapp.db.Subject;
import com.lucreziagrassi.androidapp.generic.TimePickerFragment;
import com.lucreziagrassi.androidapp.main.MainActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewLessonFragment extends Fragment implements View.OnClickListener {

    private Lesson currentLesson;

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

        for (Subject subject : DatabaseManager.getDatabase().getSubjectDao().getAll())
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
        //spinner.setOnItemSelectedListener(this);

        // Set modifying lesson if present
        if (this.getArguments() != null) {
            Integer currentLessonID = (Integer) this.getArguments().get("currentLesson");

            if (currentLessonID != null)
                this.currentLesson = DatabaseManager.getDatabase().getLessonDao().get(currentLessonID);
        }

        if (this.currentLesson != null) {
            Spinner spinner2 = getView().findViewById(R.id.subjects_spinner);

            for (int i = 0; i < spinner2.getCount(); i++) {
                String item = (String) spinner2.getItemAtPosition(i);

                if (item.equals(currentLesson.getSubject()))
                    spinner2.setSelection(i);
            }

            ((EditText) getView().findViewById(R.id.location)).setText(currentLesson.getLocation());
            ((EditText) getView().findViewById(R.id.start_hour)).setText(currentLesson.getStartHour());
            ((EditText) getView().findViewById(R.id.end_hour)).setText(currentLesson.getEndHour());

            ((TextView) getView().findViewById(R.id.addNewLessonText)).setText(R.string.modify_button);
            getActivity().setTitle(R.string.new_lesson_modify_fragment_name);
        }
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
        String subjectName = (String) ((Spinner) getView().findViewById(R.id.subjects_spinner)).getSelectedItem();

        Subject subject = null;

        for (Subject sub : DatabaseManager.getDatabase().getSubjectDao().getAll()) {
            if (sub.getSubject().equals(subjectName))
                subject = sub;
        }

        if (subject == null) {
            Toast.makeText(getActivity(), "Seleziona una materia", Toast.LENGTH_SHORT).show();
            return;
        }

        String professor = subject.getProfessor();

        String location = ((EditText) getView().findViewById(R.id.location)).getText().toString();
        Integer color = subject.getColor();
        String startHour = ((EditText) getView().findViewById(R.id.start_hour)).getText().toString();
        String endHour = ((EditText) getView().findViewById(R.id.end_hour)).getText().toString();
        Integer day = getArguments().getInt("selectedDay");

        if (!subject.equals("Seleziona una materia") && !professor.isEmpty() && !location.isEmpty() && !startHour.isEmpty() && !endHour.isEmpty()) {
            // Check ora fine > ora inizio

            try {
                DateFormat df = new SimpleDateFormat("HH:mm", Locale.ITALY);
                Date startDate = df.parse(startHour);
                Date endDate = df.parse(endHour);

                if (startDate.getTime() >= endDate.getTime()) {
                    Toast.makeText(getActivity(), "Orario inserito non valido", Toast.LENGTH_SHORT).show();
                    return;
                }
            } catch (ParseException pe) {
            }

            // Cancello la vecchia lezione in fase di modifica
            if (currentLesson != null)
                DatabaseManager.getDatabase().getLessonDao().delete(currentLesson);

            // Se i dati sono validi, creo l'esame
            Lesson newLesson = new Lesson(0, subjectName, location, startHour, endHour, day);
            DatabaseManager.getDatabase().getLessonDao().insert(newLesson);
            Toast.makeText(getActivity(), "Lezione aggiunta con successo!", Toast.LENGTH_SHORT).show();

            // Chiude la tastiera
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocusedView = getActivity().getCurrentFocus();
            if (currentFocusedView != null)
                inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            // Aggiorna la timetable
            ((MainActivity) getActivity()).getTimetableFragment().updateTimetableRecords();

            // Ritorna indietro
            getFragmentManager().popBackStack();
        } else Toast.makeText(getActivity(), "Riempi tutti i campi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

    /*
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String subject = adapterView.getItemAtPosition(position).toString();
        if (position > 0)
            Toast.makeText(adapterView.getContext(), "Hai selezionato: " + subject, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}
    */
}
