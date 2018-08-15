package com.lucreziagrassi.androidapp.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.PassedExam;
import com.lucreziagrassi.androidapp.db.User;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private static int STORAGE_PERMISSION_CODE = 1;
    private static int RESULT_LOAD_IMAGE = 1;

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
        ((TextView) ((NavigationView)getActivity().findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.nav_badge_number)).setText(user.getBadge_number());
        ((NavigationView) getActivity().findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.profile_image).setOnClickListener(this);
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

        ((TextView) getView().findViewById(R.id.avgExams)).setText((Math.round(avgPonderata * 100) / 100.0) + "");
        ((TextView) ((NavigationView)getActivity().findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.nav_avg)).setText((Math.round(avgPonderata * 100) / 100.0) + "");
        ((TextView) getView().findViewById(R.id.passedExamCount)).setText(passedExams.size() + "");

        int degreeVote = (int) Math.round(avgPonderata * 11 / 3);
        ((ProgressBar) getView().findViewById(R.id.votoLaureaProgressBar)).setProgress(degreeVote);
        ((TextView) getView().findViewById(R.id.votoLaureaProgressBarText)).setText(degreeVote + "/" + 110);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_image:
                //in realta dovrebbe controllare se ho gia i permessi e la fa partire
                if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
                //se non ho i permessi li richiedo
                else requestPermission();
                break;
        }
    }

    private void requestPermission(){
        //controllo se è necessario mostrare un Dialog per spiegare perchè servono i permessi
        if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(getContext()).setTitle("Richiesta di permesso")
            .setMessage("Questa applicazione necessita il permesso di leggere la memoria per poter modificare" +
            "l'immagine del profilo")
            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                }
            })
            .setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).create().show();
        } else requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            } else Toast.makeText(getActivity(), "Permesso di accedere allo storage negato", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //questa funzione carica l'immagine al posto dell'altra
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Context applicationContext = MainActivity.getContextOfApplication();
            Cursor cursor = applicationContext.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            // String picturePath contains the path of selected Image
            ImageView profileImage = ((NavigationView) getActivity().findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.profile_image);
            profileImage.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
}

