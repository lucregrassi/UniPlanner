package com.lucreziagrassi.androidapp.timetable;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.Lesson;

import java.util.List;

public class TimetableDayFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public TimetableDayFragment() {
    }

    public static TimetableDayFragment newInstance(int sectionNumber) {
        Log.d("TimetableDay", "Created tab: " + sectionNumber);

        TimetableDayFragment fragment = new TimetableDayFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("TimetableDay", "Opened tab: " + getArguments().getInt(ARG_SECTION_NUMBER));

        View view = inflater.inflate(R.layout.swipe_menu_listview, container, false);
        setHasOptionsMenu(true);

        SwipeMenuListView futureExams = view.findViewById(R.id.swipeview);

        List<Lesson> lessonList = DatabaseManager.getDatabase().getLessonDao().getLessonOfDay(getArguments().getInt(ARG_SECTION_NUMBER));

        TimetableListAdapter adapter = new TimetableListAdapter(getActivity(), R.layout.timetable_list_adapter, lessonList);

        futureExams.setAdapter(adapter);

        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}