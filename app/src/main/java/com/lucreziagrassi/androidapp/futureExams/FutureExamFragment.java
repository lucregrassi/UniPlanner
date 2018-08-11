package com.lucreziagrassi.androidapp.futureExams;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.FutureExam;
import com.lucreziagrassi.androidapp.db.PassedExam;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class FutureExamFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    Calendar myCalendar = Calendar.getInstance();
    EditText edittext = (EditText) getActivity().findViewById(R.id.datepicker);

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int month,
                              int day) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, day);
            updateLabel();
        }
    };

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext.setText(sdf.format(myCalendar.getTime()));
    }

    public FutureExamFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.future_exam, container, false);
        getActivity().setTitle(R.string.new_exam_fragment_name);
        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void onAddFutureExamClick() {
        // Prendo le stringhe dei textView
        String nome = ((EditText) getView().findViewById(R.id.exam_name)).getText().toString();
        String data = ((EditText) getView().findViewById(R.id.datepicker)).getText().toString();
        Integer cfu = Integer.parseInt(((EditText) getView().findViewById(R.id.exam_cfu)).getText().toString());

        if (!nome.equals("") && !data.equals("") && cfu != 0) {
            // Se i dati sono validi, creo l'esame
            FutureExam newFutureExam = new FutureExam(0, nome, data, cfu);
            DatabaseManager.getDatabase().getFutureExamDao().insert(newFutureExam);

            // Chiude la tastiera
            InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

            View currentFocusedView = getActivity().getCurrentFocus();

            if (currentFocusedView != null)
                inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            // Ritorna al libretto
            getFragmentManager().popBackStack();
        } else {
            Toast.makeText(getContext(), "Riempi tutti i campi", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addFutureExam:
                onAddFutureExamClick();
                break;
        }
    }
}
