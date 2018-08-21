package com.lucreziagrassi.androidapp.timetable;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

public class TimetableFragment extends Fragment {

    private NewLessonFragment newLessonFragment = null;

    private ViewPagerAdapter adapter;
    private NonSwipeableViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.timetable_fragment, container, false);
        setHasOptionsMenu(true);

        // Set up the ViewPager with the sections adapter.
        mViewPager = view.findViewById(R.id.container);

        adapter = new ViewPagerAdapter(getChildFragmentManager());
        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newLessonFragment = new NewLessonFragment();

                Bundle selectedDay = new Bundle();
                selectedDay.putInt("selectedDay", mViewPager.getCurrentItem());

                newLessonFragment.setArguments(selectedDay);

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, newLessonFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    public void updateTimetableRecords() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.timetable_fragment_name);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.add_button || super.onOptionsItemSelected(item);
    }


    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public Fragment getItem(int position) {
            return TimetableDayFragment.newInstance(position);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch(position) {
                case 0:
                    return "LUN";

                case 1:
                    return "MAR";

                case 2:
                    return "MER";

                case 3:
                    return "GIO";

                case 4:
                    return "VEN";

                case 5:
                    return "SAB";

                default:
                    return "";
            }
        }
    }
}
