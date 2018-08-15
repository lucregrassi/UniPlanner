package com.lucreziagrassi.androidapp.main;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.booklet.BookletFragment;
import com.lucreziagrassi.androidapp.booklet.PassedExamFragment;
import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.FutureExam;
import com.lucreziagrassi.androidapp.db.Lesson;
import com.lucreziagrassi.androidapp.db.PassedExam;
import com.lucreziagrassi.androidapp.db.Subject;
import com.lucreziagrassi.androidapp.futureExams.FutureExamFragment;
import com.lucreziagrassi.androidapp.futureExams.FutureExamsFragment;
import com.lucreziagrassi.androidapp.subjects.NewSubjectFragment;
import com.lucreziagrassi.androidapp.subjects.SubjectsFragment;
import com.lucreziagrassi.androidapp.timer.TimerFragment;
import com.lucreziagrassi.androidapp.timetable.NewLessonFragment;
import com.lucreziagrassi.androidapp.timetable.TimetableFragment;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        BookletFragment.OnFragmentInteractionListener,
        PassedExamFragment.OnFragmentInteractionListener,
        NewLessonFragment.OnFragmentInteractionListener,
        NewSubjectFragment.OnFragmentInteractionListener,
        FutureExamFragment.OnFragmentInteractionListener {

    private HomeFragment homeFragment = null;
    private TimerFragment timerFragment = null;
    private BookletFragment bookletFragment = null;
    private FutureExamsFragment futureExamsFragment = null;
    private SubjectsFragment subjectsFragment = null;
    private TimetableFragment timetableFragment = null;

    public static Context contextOfApplication;
    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        // Chiude la tastiera aprendo il menù
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                // Chiude la tastiera
                InputMethodManager inputManager = (InputMethodManager) drawerView.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                View currentFocusedView = drawerView;
                if (currentFocusedView != null)
                    inputManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // TODO: Rimuovere, debug only
        Lesson l = new Lesson(0, "Fisica Matematica", "Angelo Morro", "E" + new Random().nextInt((7 - 1) + 1), Color.GREEN, "9:00", "11:00", new Random().nextInt((6 - 1) + 1) + 1);
        DatabaseManager.getDatabase().getLessonDao().insert(l);

        if(homeFragment == null)
            homeFragment = new HomeFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.content, homeFragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // int containerID = ((ViewGroup)(getView().getParent())).getId();

        if (id == R.id.nav_profile) {
            if(homeFragment == null)
                homeFragment = new HomeFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.content, homeFragment).commit();

        } else if (id == R.id.nav_schedule) {

            if(timetableFragment == null)
                timetableFragment = new TimetableFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.content, timetableFragment).commit();

        } else if (id == R.id.nav_exams) {

            if(futureExamsFragment == null)
                futureExamsFragment = new FutureExamsFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.content, futureExamsFragment).commit();
        } else if (id == R.id.nav_votes) {

            if(bookletFragment == null)
                bookletFragment = new BookletFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.content, bookletFragment).commit();
        }else if (id == R.id.nav_subjects) {

            if (subjectsFragment == null)
                subjectsFragment = new SubjectsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content, subjectsFragment).commit();
        } else if (id == R.id.nav_timer) {

            if(timerFragment == null)
                timerFragment = new TimerFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content, timerFragment).commit();
        }else if (id == R.id.nav_settings){
            getFragmentManager().beginTransaction()
                    .replace(R.id.content, new SettingsFragment())
                    .commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }

}
