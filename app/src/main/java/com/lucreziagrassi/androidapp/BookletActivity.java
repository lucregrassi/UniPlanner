package com.lucreziagrassi.androidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BookletActivity extends AppCompatActivity {

    private static final String TAG = "BookletActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booklet);
        Log.d(TAG, "onCreate:Started.");
        ListView passedExams = (ListView) findViewById(R.id.listView);

        Exam a = new Exam ("Sviluppo Applicazioni Web", "30 e lode", "10/06/18",6);
        Exam b = new Exam ("Controllo Digitale", "30", "20/06/18",6);

        ArrayList<Exam> examList = new ArrayList<>();
        examList.add(a);
        examList.add(b);

        ExamListAdapter adapter = new ExamListAdapter(this, R.layout.list_adapter_view, examList);
        passedExams.setAdapter(adapter);
    }
}
