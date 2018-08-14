package com.lucreziagrassi.androidapp.futureExams;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lucreziagrassi.androidapp.generic.DatePickerFragment;
import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.FutureExam;

public class FutureExamFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private OnFragmentInteractionListener mListener;

    public FutureExamFragment() {
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
        CardView addFutureExamButton = (CardView) getView().findViewById(R.id.addFutureExam);
        addFutureExamButton.setOnClickListener(this);
        EditText exam_date = (EditText) getView().findViewById(R.id.exam_date);
        exam_date.setOnClickListener(this);

        final String[] subjects = new String[]{
                "Seleziona una materia",
                "Analisi",
                "Teoria dei sistemi",
                "Fisica Generale",
                "Geometria",
                "+"
        };
        Spinner spinner = (Spinner) getView().findViewById(R.id.subjects_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                subjects) {
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
        View view = inflater.inflate(R.layout.future_exam, container, false);
        getActivity().setTitle(R.string.new_exam_fragment_name);
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
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.add_button);
        item.setVisible(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_home_drawer, menu);
        super.onCreateOptionsMenu(menu, inflater);
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
        String materia = ((Spinner) getView().findViewById(R.id.subjects_spinner)).getSelectedItem().toString();
        String cfu = ((EditText) getView().findViewById(R.id.exam_cfu)).getText().toString();
        String data = ((EditText) getView().findViewById(R.id.exam_date)).getText().toString();

        if (!materia.equals("Seleziona una materia") && !data.isEmpty() && !cfu.isEmpty()) {
            Integer inCfu = Integer.parseInt(cfu);
            if (inCfu > 0) {
                // Se i dati sono validi, creo l'esame
                FutureExam newFutureExam = new FutureExam(0, materia, data, inCfu);
                DatabaseManager.getDatabase().getFutureExamDao().insert(newFutureExam);

                // Chiude la tastiera
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                View currentFocusedView = getActivity().getCurrentFocus();

                if (currentFocusedView != null)
                    inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                // Ritorna al libretto
                getFragmentManager().popBackStack();
            } else Toast.makeText(getActivity(), "Il valore di CFU inserito non è valido", Toast.LENGTH_SHORT).show();
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
