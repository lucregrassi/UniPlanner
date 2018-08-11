package com.lucreziagrassi.androidapp.timetable;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment() {
    }

    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.swipe_menu_listview, container, false);
        setHasOptionsMenu(true);

        SwipeMenuListView futureExams = (SwipeMenuListView) view.findViewById(R.id.swipeview);

        List<Lesson> lessonList = DatabaseManager.getDatabase().getLessonDao().getAll();

        TimetableListAdapter adapter = new TimetableListAdapter(getActivity(), R.layout.timetable_list_adapter, lessonList);

        futureExams.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // create "modify" item
                SwipeMenuItem openItem = new SwipeMenuItem(getActivity().getApplicationContext());
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

                // create "add to booklet" item
                SwipeMenuItem confirmItem = new SwipeMenuItem(
                        getActivity().getApplicationContext());
                // set item background
                confirmItem.setBackground(new ColorDrawable(Color.rgb(0x00,
                        0xe6, 0x00)));
                // set item width
                confirmItem.setWidth(280);
                confirmItem.setTitle("Fatto");
                confirmItem.setTitleSize(18);
                confirmItem.setTitleColor(Color.WHITE);
                // set a icon
                confirmItem.setIcon(R.drawable.ic_check);
                // add to menu
                menu.addMenuItem(confirmItem);

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
        futureExams.setMenuCreator(creator);

        futureExams.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        break;
                    case 1:
                        // delete
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}