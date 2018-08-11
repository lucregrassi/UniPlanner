package com.lucreziagrassi.androidapp;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.lucreziagrassi.androidapp.booklet.BookletFragment;
import com.lucreziagrassi.androidapp.booklet.PassedExamFragment;
import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.FutureExam;
import com.lucreziagrassi.androidapp.db.PassedExam;
import com.lucreziagrassi.androidapp.db.Subject;
import com.lucreziagrassi.androidapp.futureExams.FutureExamFragment;
import com.lucreziagrassi.androidapp.futureExams.FutureExamsFragment;
import com.lucreziagrassi.androidapp.home.HomeFragment;
import com.lucreziagrassi.androidapp.splash.SplashActivity;
import com.lucreziagrassi.androidapp.subjects.NewSubjectFragment;
import com.lucreziagrassi.androidapp.subjects.SubjectsFragment;
import com.lucreziagrassi.androidapp.timer.TimerFragment;
import com.lucreziagrassi.androidapp.weekCalendar.WeekFragment;

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
    private WeekFragment weekFragment = null;
    private PreferenceFragment preferenceFragment = null;

    private SplashActivity splashActivity = null;

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
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // TODO: Rimuovere, debug only: aggiunge un esame al db
        PassedExam a = new PassedExam(0,"Sviluppo Applicazioni Web", "30 e lode", "25/05/18", 6);
        DatabaseManager.getDatabase().getPassedExamDao().insert(a);

        FutureExam b = new FutureExam(0, "Controllo Digitale", "25/09/18", 6);
        DatabaseManager.getDatabase().getFutureExamDao().insert(b);

        Subject s = new Subject (0, "Dispositivi e circuiti elettronici", "Daniele Caviglia", Color.RED);
        DatabaseManager.getDatabase().getSubjectDao().insert(s);

        if(homeFragment == null)
            homeFragment = new HomeFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.content, homeFragment).commit();
    }

    @Override
    public void onStart()
    {
        super.onStart();

        ((TextView) findViewById(R.id.welcomeText)).setText("Bentornata " + DatabaseManager.getDatabase().getUserDao().getUser().getName() + "!");
        
        // TODO: Carica anche gli altri dati dal db
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

            if(weekFragment == null)
                weekFragment = new WeekFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.content, weekFragment).commit();

        } else if (id == R.id.nav_exams) {

            if(futureExamsFragment == null)
                futureExamsFragment = new FutureExamsFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.content, futureExamsFragment).commit();
        } else if (id == R.id.nav_votes) {

            if(bookletFragment == null)
                bookletFragment = new BookletFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.content, bookletFragment).commit();
        } else if (id == R.id.nav_timer) {

            if(timerFragment == null)
                timerFragment = new TimerFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content, timerFragment).commit();
        } else if (id == R.id.nav_subjects) {

            if(subjectsFragment == null)
                subjectsFragment = new SubjectsFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content, subjectsFragment).commit();

        }else if (id == R.id.nav_settings){
            getFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new SettingsFragment())
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
