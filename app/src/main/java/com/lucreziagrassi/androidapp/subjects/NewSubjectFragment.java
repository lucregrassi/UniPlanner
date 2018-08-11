package com.lucreziagrassi.androidapp.subjects;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lucreziagrassi.androidapp.R;

import yuku.ambilwarna.AmbilWarnaDialog;

public class NewSubjectFragment extends Fragment implements View.OnClickListener {

    ImageView mImageView;
    Integer mDefaultColor;

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
        mDefaultColor = ContextCompat.getColor(getActivity(), R.color.colorPrimary);

        CardView colorPickerButton = getActivity().findViewById(R.id.color_picker_button);
        colorPickerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.color_picker_button:
                openColorPicker();
                break;
        }
    }


    public void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(getActivity(), mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor = color;
                mImageView.setBackgroundColor(color);
            }
        });
        colorPicker.show();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
