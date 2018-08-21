package com.lucreziagrassi.androidapp.timetable;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.Lesson;

import java.util.List;

public class TimetableDayFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private TimetableListAdapter adapter;
    private List<Lesson> lessonList;
    private SwipeMenuListView futureExams;

    public TimetableDayFragment() { }

    public static TimetableDayFragment newInstance(int sectionNumber)
    {
        TimetableDayFragment fragment = new TimetableDayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        lessonList = DatabaseManager.getDatabase().getLessonDao().getLessonOfDay(getArguments().getInt(ARG_SECTION_NUMBER));
        adapter = new TimetableListAdapter(getContext(), R.layout.timetable_list_adapter, lessonList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.swipe_menu_listview, container, false);
        setHasOptionsMenu(true);

        futureExams = view.findViewById(R.id.swipeview);
        futureExams.setAdapter(adapter);

        return view;
    }
}