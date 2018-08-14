package com.lucreziagrassi.androidapp.main;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.PassedExam;
import com.lucreziagrassi.androidapp.db.User;

import java.util.List;


public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home, container, false);
        getActivity().setTitle(R.string.home_fragment_name);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Imposta i dati dell'utente
        User user = DatabaseManager.getDatabase().getUserDao().getUser();
        ((TextView) getView().findViewById(R.id.university_name)).setText(user.getUniversity());
        ((TextView) getView().findViewById(R.id.degree_course)).setText(user.getCourse());
        ((TextView) ((NavigationView)getActivity().findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.nav_username)).setText(user.getName() + " " + user.getSurname());

        //Calcola media, cfu e voto stimato
        Double avgPonderata = 0.0;
        Integer sumCFU = 0;

        List<PassedExam> passedExams = DatabaseManager.getDatabase().getPassedExamDao().getAll();

        for (PassedExam passedExam : passedExams) {
            avgPonderata += passedExam.getVote() * passedExam.getCFU();
            sumCFU += passedExam.getCFU();
        }

        avgPonderata /= sumCFU;

        ((ProgressBar) getView().findViewById(R.id.cfuProgressBar)).setMax(user.getCFU());
        ((ProgressBar) getView().findViewById(R.id.cfuProgressBar)).setProgress(sumCFU);
        ((TextView) getView().findViewById(R.id.cfuProgressBarText)).setText(sumCFU + "/" + user.getCFU());

        ((TextView) getView().findViewById(R.id.avgExams)).setText((Math.round(avgPonderata * 100) / 100) + "");
        ((TextView) ((NavigationView)getActivity().findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.nav_avg)).setText((Math.round(avgPonderata * 100) / 100) + "");
        ((TextView) getView().findViewById(R.id.passedExamCount)).setText(passedExams.size() + "");

        int degreeVote = (int) Math.round(avgPonderata * 11 / 3);
        ((ProgressBar) getView().findViewById(R.id.votoLaureaProgressBar)).setProgress(degreeVote);
        ((TextView) getView().findViewById(R.id.votoLaureaProgressBarText)).setText(degreeVote + "/" + 110);
    }
}
