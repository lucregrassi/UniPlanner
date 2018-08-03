package com.lucreziagrassi.androidapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

public class BookletFragment extends Fragment {

    private static final String TAG = "BookletFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_booklet, container,false);

        ListView passedExams = (ListView)view.findViewById(R.id.listView);

        Exam a = new Exam ("Sviluppo Applicazioni Web", "30 e lode", "10/06/18",6);
        Exam b = new Exam ("Controllo Digitale", "30", "20/06/18",6);

        ArrayList<Exam> examList = new ArrayList<>();
        examList.add(a);
        examList.add(b);

        ExamListAdapter adapter = new ExamListAdapter(getActivity(), R.layout.list_adapter_view, examList);
        passedExams.setAdapter(adapter);

        return view;
    }
}
