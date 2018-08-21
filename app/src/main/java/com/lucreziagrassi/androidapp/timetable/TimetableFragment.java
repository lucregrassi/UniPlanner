package com.lucreziagrassi.androidapp.timetable;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.timer.TimerFragment;

import java.util.ArrayList;
import java.util.List;

public class TimetableFragment extends Fragment
{
    private NewLessonFragment newLessonFragment = null;

    private ViewPager mViewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.timetable_fragment, container, false);

        // Set up the ViewPager with the sections adapter.
        mViewPager = view.findViewById(R.id.container);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(TimetableDayFragment.newInstance(0), "LUN");
        adapter.addFragment(TimetableDayFragment.newInstance(1), "MAR");
        adapter.addFragment(TimetableDayFragment.newInstance(2), "MER");
        adapter.addFragment(TimetableDayFragment.newInstance(3), "GIO");
        adapter.addFragment(TimetableDayFragment.newInstance(4), "VEN");
        adapter.addFragment(TimetableDayFragment.newInstance(5), "SAB");
        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newLessonFragment = new NewLessonFragment();

                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content, newLessonFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.timetable_fragment_name);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add_button)
            return true;

        return super.onOptionsItemSelected(item);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
