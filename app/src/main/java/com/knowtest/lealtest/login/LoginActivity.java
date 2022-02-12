package com.knowtest.lealtest.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.knowtest.lealtest.R;
import com.knowtest.lealtest.cadastro.CadastroActivity;
import com.knowtest.lealtest.model.User;
import com.knowtest.lealtest.api.CredentialApi;

public class LoginActivity extends AppCompatActivity {

    private Button loginBotton;
    private Button registerButton;
    private EditText email;
    private EditText password;
    private User user;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getSupportActionBar().hide();
        registerButton = findViewById(R.id.botão_cadastro_login);
        loginBotton= findViewById(R.id.botao_entrar_login);
        email=findViewById(R.id.email_login);
        password = findViewById(R.id.senha_login);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
                startActivity(intent);
            }
        });


        loginBotton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String em= email.getText().toString();
                String pas=password.getText().toString();
                if ( !em.isEmpty() ){
                    if ( !pas.isEmpty() ){
                        user = new User();
                        user.setEmail(em);
                        user.setSenha(pas);
                        logador();
                    }else {
                        Toast.makeText(LoginActivity.this,
                                "Preencha a senha",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this,
                            "Preencha email!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void logador(){
        auth = CredentialApi.Companion.getFirebaseAuth();
        auth.signInWithEmailAndPassword(user.getEmail(),user.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(LoginActivity.this,
                            "logir ok",
                            Toast.LENGTH_SHORT).show();
                    finish();


                }else {
                    String msg="";
                    try {
                        throw task.getException();

                    } catch (FirebaseAuthEmailException e) {
                        msg = "esse email não está cadastrada";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        msg = "Emeil e senha não conferem";
                    } catch (Exception e) {
                        msg="digite um email válido "+e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(LoginActivity.this,
                            msg,
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    public void sairTelaLogin(View v) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Deseja Sair do Aplicativo?");
        builder.setCancelable(true);
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                finishAffinity();
                System.exit(0);
            }
        });
        builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
}