package com.lucreziagrassi.androidapp;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.User;
import com.lucreziagrassi.androidapp.splash.SplashActivity;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);

        CardView iniziaButton = (CardView)findViewById(R.id.cardView);
        iniziaButton.setOnClickListener(this);
    }

    public void onIniziaClick()
    {
        // Prendo le stringhe dei textView
        String nome = ((EditText)findViewById(R.id.nomeText)).getText().toString();
        String cognome = ((EditText)findViewById(R.id.cognomeText)).getText().toString();
        String university = ((EditText)findViewById(R.id.universityText)).getText().toString();
        String corso = ((EditText)findViewById(R.id.corsoText)).getText().toString();
        String matricola = ((EditText)findViewById(R.id.matricolaText)).getText().toString();
        Integer cfu = Integer.parseInt(((EditText)findViewById(R.id.cfuText)).getText().toString());

        if(!nome.equals("") && !matricola.equals("") && !corso.equals("") && cfu != 0)
        {
            // Se i dati sono validi, creo l'utente
            User newUser = new User(0, nome, cognome, university, corso, matricola, cfu);
            DatabaseManager.getDatabase().getUserDao().setUser(newUser);

            // Cambio activity
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.cardView:
                onIniziaClick();
                break;
        }
    }
}
