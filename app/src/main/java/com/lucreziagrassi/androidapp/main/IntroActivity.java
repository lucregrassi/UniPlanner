package com.lucreziagrassi.androidapp.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.lucreziagrassi.androidapp.R;
import com.lucreziagrassi.androidapp.db.DatabaseManager;
import com.lucreziagrassi.androidapp.db.User;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);

        CardView startButton = (CardView) findViewById(R.id.start_button);
        startButton.setOnClickListener(this);
    }

    public void onStartButtonClick() {
        // Prendo le stringhe dei textView
        String nome = ((EditText) findViewById(R.id.nomeText)).getText().toString();
        String cognome = ((EditText) findViewById(R.id.cognomeText)).getText().toString();
        String university = ((EditText) findViewById(R.id.universityText)).getText().toString();
        String corso = ((EditText) findViewById(R.id.corsoText)).getText().toString();
        String matricola = ((EditText) findViewById(R.id.matricolaText)).getText().toString();
        String cfu = ((EditText) findViewById(R.id.cfuText)).getText().toString();

        if (!nome.isEmpty() && !matricola.isEmpty() && !corso.isEmpty() && !cfu.isEmpty()) {
            Integer intCfu = Integer.parseInt(cfu);
            if (intCfu > 0) {
                // Se i dati sono validi, creo l'utente
                User newUser = new User(0, nome, cognome, university, corso, matricola, intCfu, 0);
                DatabaseManager.getDatabase().getUserDao().setUser(newUser);

                // Cambio activity
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
            } else
                Toast.makeText(this, "Il valore di CFU inserito non è valido", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Riempi tutti i campi", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_button:
                onStartButtonClick();
                break;
        }
    }
}
