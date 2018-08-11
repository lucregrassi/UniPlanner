package com.lucreziagrassi.androidapp.futureExams;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.FutureExam;

import java.util.List;


public class FutureExamsFragment extends Fragment {

    private static final String TAG = "FutureExamsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.swipe_menu_listview, container, false);
        getActivity().setTitle(R.string.future_exams_fragment_name);
        setHasOptionsMenu(true);

        SwipeMenuListView futureExams = (SwipeMenuListView) view.findViewById(R.id.swipeview);

        List<FutureExam> futureExamList = DatabaseManager.getDatabase().getFutureExamDao().getAll();

        FutureExamsListAdapter adapter = new FutureExamsListAdapter(getActivity(), R.layout.future_exams_list_adapter, futureExamList);

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

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle("Prossimi Esami");
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_add_button, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_button:
                Fragment newFragment = new FutureExamFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();

                transaction.replace(R.id.content, newFragment);
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
