package com.lucreziagrassi.androidapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import yuku.ambilwarna.AmbilWarnaDialog;

public class NewLessonFragment extends Fragment implements View.OnClickListener {

    RelativeLayout mLayout;
    Integer mDefaultColor;
    Button mButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.new_lesson, container, false);
        getActivity().setTitle(R.string.new_lesson_fragment_name);

        mLayout = getActivity().findViewById(R.id.new_subject_layout);
        mDefaultColor = ContextCompat.getColor(getActivity(), R.color.colorPrimary);
        mButton = getActivity().findViewById(R.id.color_picker_button);
        mButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        openColorPicker();
    }

    public void openColorPicker(){
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(getActivity(), mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor = color;
                mLayout.setBackgroundColor(color);
            }
        });
        colorPicker.show();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
