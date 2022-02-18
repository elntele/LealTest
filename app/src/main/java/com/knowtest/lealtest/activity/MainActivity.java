package com.knowtest.lealtest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.knowtest.lealtest.R;
import com.knowtest.lealtest.model.Treino;
import com.knowtest.lealtest.singletonInstances.CredentialApi;
import com.knowtest.lealtest.api.FireStoreApi;
import com.knowtest.lealteste.Activity.model.Exercicio;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FireStoreApi f;
    private List<Exercicio> exercicios = new ArrayList<>();
    private List<Treino> treinos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        f = f.fireStoreViewModel(getApplicationContext());
        f.getExerciciosInBack();
        consultabanco();
    }

    public void consultabanco() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                exercicios = f.getDb().IexercicioDao().getAll();
                treinos = f.getDb().ItreinoDao().getAll();
            }
        }).start();
    }

    @Override
    public void onStart() {
        super.onStart();
        routing();
    }

    public void routing() {
        List<Treino> ts = f.getTreinoout();
        FirebaseAuth firebaseAuth = CredentialApi.Companion.getFirebaseAuth();
        if (((firebaseAuth.getCurrentUser() == null))) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        } else if (!(ts.size() > 0)) {
            Intent intent = new Intent(getApplicationContext(), WaitActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), TelaInicialActivity.class);
            intent.putParcelableArrayListExtra("array", (ArrayList<Treino>) ts);
            startActivity(intent);
        }
    }
}