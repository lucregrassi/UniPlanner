package com.lucreziagrassi.androidapp.subjects;

import android.content.Context;
import android.net.Uri;
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
import android.widget.Spinner;
import android.widget.Toast;

import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.Lesson;
import com.lucreziagrassi.androidapp.db.PassedExam;
import com.lucreziagrassi.androidapp.db.Subject;

import yuku.ambilwarna.AmbilWarnaDialog;

public class NewSubjectFragment extends Fragment implements View.OnClickListener {

    ImageView mImageView;
    Integer subjectColor;

    public NewSubjectFragment() {
        //empty constructor
    }

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
        String professor  = ((EditText) getView().findViewById(R.id.prof_name)).getText().toString();
        String cfu = ((EditText) getView().findViewById(R.id.exam_cfu)).getText().toString();
        Integer color = subjectColor;

        if (!subject.isEmpty() && !professor.isEmpty() && !cfu.isEmpty()) {
            Integer intCfu = Integer.parseInt(cfu);
            if(intCfu > 0){
            // Se i dati sono validi, creo l'esame
            Subject newSubject = new Subject(0, subject, professor, intCfu, color);
            DatabaseManager.getDatabase().getSubjectDao().insert(newSubject);

            // Chiude la tastiera
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocusedView = getActivity().getCurrentFocus();
            if (currentFocusedView != null)
                inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            // Ritorna al libretto
            getFragmentManager().popBackStack();
        }else Toast.makeText(getActivity(), "Il valore di CFU inserito non è valido", Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(getActivity(), "Riempi tutti i campi", Toast.LENGTH_SHORT).show();
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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
