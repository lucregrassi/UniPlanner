package com.lucreziagrassi.androidapp.booklet;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.PassedExam;
import com.lucreziagrassi.androidapp.db.User;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PassedExamFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PassedExamFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PassedExamFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PassedExamFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PassedExamFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PassedExamFragment newInstance(String param1, String param2) {
        PassedExamFragment fragment = new PassedExamFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStart()
    {
        super.onStart();

        CardView addPassedExamButton = (CardView) getView().findViewById(R.id.addPassedExam);
        addPassedExamButton.setOnClickListener(this);
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.activity_home_drawer, menu);
        super.onCreateOptionsMenu(menu,inflater);
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

    public void onAddPassedExamClick()
    {
        // Prendo le stringhe dei textView
        String nome = ((EditText) getView().findViewById(R.id.exam_name)).getText().toString();
        String voto = ((EditText) getView().findViewById(R.id.exam_vote)).getText().toString();
        Integer cfu = Integer.parseInt(((EditText) getView().findViewById(R.id.exam_cfu)).getText().toString());
        String data = ((EditText) getView().findViewById(R.id.exam_date)).getText().toString();

        if(!nome.equals("") && !data.equals("") && !voto.equals("") && cfu != 0)
        {
            // Se i dati sono validi, creo l'esame
            PassedExam newPassedExam = new PassedExam(0, nome, voto, data, cfu);

            DatabaseManager.getDatabase().getPassedExamDao().insert(newPassedExam);
        }

        // Chiude la tastiera
        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);

        View currentFocusedView = getActivity().getCurrentFocus();

        if (currentFocusedView != null)
            inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);


        // Ritorna al libretto
        getFragmentManager().popBackStack();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.addPassedExam:
                onAddPassedExamClick();
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
