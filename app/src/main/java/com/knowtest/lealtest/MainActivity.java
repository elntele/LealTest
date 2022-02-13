package com.knowtest.lealtest;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.knowtest.lealtest.login.LoginActivity;
import com.knowtest.lealtest.api.CredentialApi;
import com.knowtest.lealtest.api.FireStoreApi;
import com.knowtest.lealtest.modeView.FireStoreViewModel;
import com.knowtest.lealtest.wait.WaitActivity;
import com.knowtest.lealteste.Activity.model.Exercicio;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FireStoreViewModel f;
    private List<Exercicio> exercicios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        f = new FireStoreViewModel(getApplicationContext());
        consultabanco();
        new Thread(new Runnable() {
            @Override
            public void run() {
                f.getExerciciosInBack();
            }
        }).start();

        consultabanco();
    }

    public void consultabanco() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                exercicios = f.getDb().IexercicioDao().getAll();

            }
        }).start();
    }

    private void getExercicios() {
        FirebaseFirestore db = FireStoreApi.Companion.getFirebaseFirestore();
        db.collection("EXERCICIO")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }


    @Override
    public void onStart() {
        super.onStart();
        consultabanco();
        routing();
    }

    public void routing() {
        FirebaseAuth firebaseAuth = CredentialApi.Companion.getFirebaseAuth();
        if (firebaseAuth.getCurrentUser() == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }else if (exercicios.size()==0){
            Intent intent = new Intent(getApplicationContext(), WaitActivity.class);
            startActivity(intent);
        }
    }
}