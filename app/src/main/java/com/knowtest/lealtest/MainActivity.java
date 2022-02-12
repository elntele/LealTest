package com.knowtest.lealtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.knowtest.lealtest.login.LoginActivity;
import com.knowtest.lealtest.viewMoldel.CredentialViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public void onStart() {

        super.onStart();
        routing();
    }

    public void routing() {
        FirebaseAuth firebaseAuth = CredentialViewModel.Companion.getFirebaseAuth();
        if (firebaseAuth.getCurrentUser() == null) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }
}