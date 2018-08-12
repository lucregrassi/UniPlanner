package com.lucreziagrassi.androidapp.timetable;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.TimePickerFragment;

public class NewLessonFragment extends Fragment implements View.OnClickListener{

    private NewLessonFragment.OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        EditText start_hour = (EditText) getView().findViewById(R.id.start_hour);
        start_hour.setOnClickListener(this);
        EditText end_hour = (EditText) getView().findViewById(R.id.end_hour);
        end_hour.setOnClickListener(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.new_lesson, container, false);
        getActivity().setTitle(R.string.new_lesson_fragment_name);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NewLessonFragment.OnFragmentInteractionListener) {
            mListener = (NewLessonFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onClick(View v) {
        DialogFragment timePicker = new TimePickerFragment();
        //newFragment.show(getActivity().getFragmentManager(), DIALOG_TIME);
        timePicker.show(getFragmentManager(), "Time Picker");
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
}
