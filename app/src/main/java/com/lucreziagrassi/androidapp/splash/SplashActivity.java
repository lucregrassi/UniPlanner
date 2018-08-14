package com.lucreziagrassi.androidapp.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lucreziagrassi.androidapp.main.IntroActivity;
import com.lucreziagrassi.androidapp.main.MainActivity;
import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.AppDatabase;
import com.lucreziagrassi.androidapp.db.DatabaseManager;

public class SplashActivity extends AppCompatActivity {

    private AppDatabase appDB = null;

    public AppDatabase getDB() {
        return appDB;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        Thread thread = new Thread() {
            public void run() {
                try {
                    long startMillis = System.currentTimeMillis();

                    // App Loading: inserire qui funzioni da eseguire all'apertura dell'app

                    // Crea o apre il db
                    DatabaseManager.initializeDatabase(getApplicationContext());

                    // Verifica se l'utente è già stato creato
                    boolean userExists = DatabaseManager.getDatabase().getUserDao().getUser() != null;

                    // Splash activity sempre visualizzata per almeno 1 secondo
                    while(System.currentTimeMillis() - startMillis < 1000)
                        sleep(100);

                    // Avvia l'app
                    if(userExists) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}