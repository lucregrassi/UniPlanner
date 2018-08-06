package com.lucreziagrassi.androidapp.home;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lucreziagrassi.androidapp.R;


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home, container,false);

        getActivity().setTitle(R.string.home_fragment_name);

        return view;
    }
}