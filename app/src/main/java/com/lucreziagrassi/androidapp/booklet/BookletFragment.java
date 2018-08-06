package com.lucreziagrassi.androidapp.booklet;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.lucreziagrassi.androidapp.MainActivity;
import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.PassedExam;

import java.util.List;

public class BookletFragment extends Fragment {

    private static final String TAG = "BookletFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.list_view, container,false);
        getActivity().setTitle(R.string.booklet_fragment_name);
        setHasOptionsMenu(true);

        ListView passedExams = (ListView)view.findViewById(R.id.listView);

        List<PassedExam> passedExamList = DatabaseManager.getDatabase().getPassedExamDao().getAll();

        PassedExamsListAdapter adapter = new PassedExamsListAdapter(getActivity(), R.layout.booklet_list_adapter, passedExamList);
        passedExams.setAdapter(adapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Libretto");
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.action_bar_add_button, menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_button:
                Fragment newFragment = new PassedExamFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.list_view_fragment, newFragment);
                transaction.addToBackStack(null);

                transaction.commit();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
