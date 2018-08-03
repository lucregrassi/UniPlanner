package com.lucreziagrassi.androidapp;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.lucreziagrassi.androidapp.booklet.BookletFragment;
import com.lucreziagrassi.androidapp.db.AppDatabase;
import com.lucreziagrassi.androidapp.db.PassedExam;
import com.lucreziagrassi.androidapp.home.HomeFragment;
import com.lucreziagrassi.androidapp.timer.TimerFragment;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private HomeFragment homeFragment = null;
    private TimerFragment timerFragment = null;
    private BookletFragment bookletFragment = null;

    private AppDatabase appDB = null;

    public AppDatabase getDB()
    {
        return appDB;
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
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Crea il db, TODO: Da spostare in splash activity in futuro
        appDB = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "uniplanner_db").allowMainThreadQueries().build();

        // TODO: Rimuovere, debug only: aggiunge un esame al db
        PassedExam a = new PassedExam(0,"Test", "30", "25/05/18", 6);
        appDB.getExamDao().insert(a);

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

        } else if (id == R.id.nav_exams) {

        } else if (id == R.id.nav_votes) {

            if(bookletFragment == null)
                bookletFragment = new BookletFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.content, bookletFragment).commit();
        } else if (id == R.id.nav_timer) {

            if(timerFragment == null)
                timerFragment = new TimerFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content, timerFragment).commit();
        } else if (id == R.id.nav_subjects) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
