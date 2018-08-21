package com.lucreziagrassi.androidapp.timetable;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.lucreziagrassi.androidapp.main.MainActivity;

import java.util.List;

public class TimetableDayFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private TimetableListAdapter adapter;
    private List<Lesson> lessonList;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.swipe_menu_listview, container, false);
        setHasOptionsMenu(true);

        final SwipeMenuListView lessons = view.findViewById(R.id.swipeview);

        lessonList = DatabaseManager.getDatabase().getLessonDao().getLessonOfDay(getArguments().getInt(ARG_SECTION_NUMBER));
        adapter = new TimetableListAdapter(getActivity(), R.layout.timetable_list_adapter, lessonList);
        lessons.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "modify" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xcc, 0xcc,
                        0xcc)));
                // set item width
                openItem.setWidth(280);
                // set item title
                openItem.setTitle("Modifica");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                openItem.setIcon(R.drawable.ic_edit);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xff,
                        0x00, 0x00)));
                // set item width
                deleteItem.setWidth(280);
                deleteItem.setTitle("Elimina");
                deleteItem.setTitleSize(18);
                deleteItem.setTitleColor(Color.WHITE);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        // set creator
        lessons.setMenuCreator(creator);

        lessons.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                // Position: indice dell'elemento
                // Index: indice del tasto su quell'elemento

                // Prende l'elemento
                Lesson selectedLesson = lessonList.get(position);

                switch (index) {
                    case 0:
                        // Modifica
                        ((MainActivity)getActivity()).getTimetableFragment().openLessonModify(selectedLesson);
                        break;
                    case 1:
                        // Elimina
                        DatabaseManager.getDatabase().getLessonDao().delete(selectedLesson);
                        // Reload view
                        ((MainActivity)getActivity()).getTimetableFragment().updateTimetableRecords();
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        return view;
    }
}