package com.lucreziagrassi.androidapp.subjects;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.Subject;

import yuku.ambilwarna.AmbilWarnaDialog;

public class NewSubjectFragment extends Fragment implements View.OnClickListener {

    private Subject currentSubject = null;

    ImageView mImageView;
    Integer subjectColor;

    public NewSubjectFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_subject, container, false);
        getActivity().setTitle(R.string.new_subject_fragment_name);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mImageView = getActivity().findViewById(R.id.chosen_color);
        subjectColor = ContextCompat.getColor(getActivity(), R.color.colorPrimary);

        CardView colorPickerButton = getActivity().findViewById(R.id.color_picker_button);
        colorPickerButton.setOnClickListener(this);
        CardView addNewSubjectButton = (CardView) getView().findViewById(R.id.addNewSubject);
        addNewSubjectButton.setOnClickListener(this);

        // Set modifying subject if present
        if (this.getArguments() != null) {
            Integer currentSubjectID = (Integer) this.getArguments().get("currentSubject");

            if (currentSubjectID != null)
                this.currentSubject = DatabaseManager.getDatabase().getSubjectDao().get(currentSubjectID);
        }

        if (this.currentSubject != null) {
            ((EditText) getView().findViewById(R.id.subject_name)).setText(currentSubject.getSubject());
            ((EditText) getView().findViewById(R.id.prof_name)).setText(currentSubject.getProfessor());
            ((EditText) getView().findViewById(R.id.exam_cfu)).setText(currentSubject.getCfu() + "");

            subjectColor = currentSubject.getColor();
            mImageView.setBackgroundColor(currentSubject.getColor());

            ((TextView) getView().findViewById(R.id.addNewSubjectText)).setText(R.string.modify_button);
            getActivity().setTitle(R.string.new_subject_modify_fragment_name);
        }
    }

    public void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(getActivity(), subjectColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                subjectColor = color;
                mImageView.setBackgroundColor(color);
            }
        });
        colorPicker.show();
    }

    public void onAddNewSubjectClick() {
        // Prendo le stringhe dei textView
        String subject = ((EditText) getView().findViewById(R.id.subject_name)).getText().toString();
        String professor = ((EditText) getView().findViewById(R.id.prof_name)).getText().toString();
        String cfu = ((EditText) getView().findViewById(R.id.exam_cfu)).getText().toString();
        Integer color = subjectColor;

        if (!subject.isEmpty() && !professor.isEmpty() && !cfu.isEmpty()) {
            Integer intCfu = Integer.parseInt(cfu);
            if (intCfu > 0) {
                // Se i dati sono validi, creo l'esame
                Subject newSubject = new Subject(0, subject, professor, intCfu, color);

                // Cancella eventuali materie da modificare e aggiorna i collegamenti
                if (currentSubject != null) {
                    Subject.updateSubjectReferences(currentSubject, newSubject);
                    DatabaseManager.getDatabase().getSubjectDao().delete(currentSubject);
                }

                // Check che il nome sia univoco
                for (Subject sub : DatabaseManager.getDatabase().getSubjectDao().getAll()) {
                    if (sub.getSubject().equals(newSubject.getSubject())) {
                        // Nome già usato
                        Toast.makeText(getActivity(), "Hai già inserito questa materia", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                DatabaseManager.getDatabase().getSubjectDao().insert(newSubject);
                Toast.makeText(getActivity(), "Materia aggiunta con successo!", Toast.LENGTH_SHORT).show();

                // Chiude la tastiera
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                View currentFocusedView = getActivity().getCurrentFocus();
                if (currentFocusedView != null)
                    inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                // Ritorna al libretto
                getFragmentManager().popBackStack();

            } else
                Toast.makeText(getActivity(), "Il valore di CFU inserito non è valido", Toast.LENGTH_SHORT).show();
        } else Toast.makeText(getActivity(), "Riempi tutti i campi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.color_picker_button:
                openColorPicker();
                break;

            case R.id.addNewSubject:
                onAddNewSubjectClick();
                break;
        }
    }
}
