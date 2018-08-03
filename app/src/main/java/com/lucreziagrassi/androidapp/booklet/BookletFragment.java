package com.lucreziagrassi.androidapp.booklet;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lucreziagrassi.androidapp.MainActivity;
import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.Exam;

import java.util.ArrayList;
import java.util.List;

public class BookletFragment extends Fragment {

    private static final String TAG = "BookletFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_booklet, container,false);

        ListView passedExams = (ListView)view.findViewById(R.id.listView);

        /*
        Exam a = new Exam ("Sviluppo Applicazioni Web", "30 e lode", "10/06/18",6);
        Exam b = new Exam ("Controllo Digitale", "30", "20/06/18",6);

        ArrayList<Exam> examList = new ArrayList<>();
        examList.add(a);
        examList.add(b);
        */

        List<Exam> examList = ((MainActivity) getActivity()).getDB().getExamDao().getAll();

        ExamListAdapter adapter = new ExamListAdapter(getActivity(), R.layout.list_adapter_view, examList);
        passedExams.setAdapter(adapter);

        return view;
    }
}
