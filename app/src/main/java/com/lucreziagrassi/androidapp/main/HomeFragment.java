package com.lucreziagrassi.androidapp.main;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.FutureExam;
import com.lucreziagrassi.androidapp.db.PassedExam;
import com.lucreziagrassi.androidapp.db.User;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;

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
        ((TextView) ((NavigationView) getActivity().findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.nav_username)).setText(user.getName() + " " + user.getSurname());
        ((TextView) ((NavigationView) getActivity().findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.nav_badge_number)).setText(user.getBadge_number());
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
        ((TextView) getView().findViewById(R.id.passedExamCount)).setText(passedExams.size() + "");

        int degreeVote = (int) Math.round(avgPonderata * 11 / 3);
        ((ProgressBar) getView().findViewById(R.id.votoLaureaProgressBar)).setProgress(degreeVote);
        ((TextView) getView().findViewById(R.id.votoLaureaProgressBarText)).setText(degreeVote + "/" + 110);

        // Setta il prossimo esame
        List<FutureExam> futureExams = DatabaseManager.getDatabase().getFutureExamDao().getAll();

        if (futureExams.size() >= 1) {
            FutureExam nextExam = futureExams.get(0);

            DateFormat df = new SimpleDateFormat("dd MMMM yyyy", Locale.ITALY);
            String formattedNextExamDate = df.format(nextExam.getDate());

            ((TextView) getView().findViewById(R.id.nextExamText)).setText(nextExam.getSubject());
            ((TextView) getView().findViewById(R.id.nextExamDate)).setText(formattedNextExamDate);
        }

        // Imposta immagine profilo
        if(user.getImage_Path() != null && user.getImage_Path() != "")
        {
            if((new File(user.getImage_Path()).exists()))
            {
                Bitmap profImage = BitmapFactory.decodeFile(user.getImage_Path());

                ImageView profileImage = ((NavigationView) getActivity().findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.profile_image);
                profileImage.setImageBitmap(getCroppedBitmap(profImage));
                scaleImage(profileImage);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.profile_image:
                // in realta dovrebbe controllare se ho gia i permessi e la fa partire
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                    requestPermission();
                else {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }
                // se non ho i permessi li richiedo
                break;
        }
    }

    private void requestPermission() {
        //controllo se è necessario mostrare un Dialog per spiegare perchè servono i permessi
         if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(getContext()).setTitle("Richiesta di permesso")
                    .setMessage("Questa applicazione necessita il permesso di leggere la memoria per poter modificare" +
                            "l'immagine del profilo")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).create().show();
        } else
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            } else
                Toast.makeText(getActivity(), "Permesso di accedere allo storage negato", Toast.LENGTH_SHORT).show();
        }
    }

    public Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }

    private void scaleImage(ImageView view) throws NoSuchElementException  {
        // Get bitmap from the the ImageView.
        Bitmap bitmap = null;

        try {
            Drawable drawing = view.getDrawable();
            bitmap = ((BitmapDrawable) drawing).getBitmap();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("No drawable on given view");
        } catch (ClassCastException e) {

        }

        // Get current dimensions AND the desired bounding box
        int width = 0;

        try {
            width = bitmap.getWidth();
        } catch (NullPointerException e) {
            throw new NoSuchElementException("Can't find bitmap on given view/drawable");
        }

        int height = bitmap.getHeight();
        int bounding = dpToPx(80);
        Log.i("Test", "original width = " + Integer.toString(width));
        Log.i("Test", "original height = " + Integer.toString(height));
        Log.i("Test", "bounding = " + Integer.toString(bounding));

        // Determine how much to scale: the dimension requiring less scaling is
        // closer to the its side. This way the image always stays inside your
        // bounding box AND either x/y axis touches it.
        float xScale = ((float) bounding) / width;
        float yScale = ((float) bounding) / height;
        float scale = (xScale <= yScale) ? xScale : yScale;
        Log.i("Test", "xScale = " + Float.toString(xScale));
        Log.i("Test", "yScale = " + Float.toString(yScale));
        Log.i("Test", "scale = " + Float.toString(scale));

        // Create a matrix for the scaling and add the scaling data
        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        // Create a new bitmap and convert it to a format understood by the ImageView
        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        width = scaledBitmap.getWidth(); // re-use
        height = scaledBitmap.getHeight(); // re-use
        BitmapDrawable result = new BitmapDrawable(scaledBitmap);
        Log.i("Test", "scaled width = " + Integer.toString(width));
        Log.i("Test", "scaled height = " + Integer.toString(height));

        // Apply the scaled bitmap
        view.setImageDrawable(result);

        // Now change ImageView's dimensions to match the scaled image
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);

        Log.i("Test", "done");
    }

    private int dpToPx(int dp) {
        float density = getActivity().getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //questa funzione carica l'immagine al posto dell'altra
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Context applicationContext = MainActivity.getContextOfApplication();
            Cursor cursor = applicationContext.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            Bitmap profImage = BitmapFactory.decodeFile(picturePath);

            // String picturePath contains the path of selected Image
            ImageView profileImage = ((NavigationView) getActivity().findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.profile_image);
            profileImage.setImageBitmap(getCroppedBitmap(profImage));
            scaleImage(profileImage);

            User user = DatabaseManager.getDatabase().getUserDao().getUser();
            user.setImage_Path(picturePath);
            DatabaseManager.getDatabase().getUserDao().setUser(user);
        }
    }
}

