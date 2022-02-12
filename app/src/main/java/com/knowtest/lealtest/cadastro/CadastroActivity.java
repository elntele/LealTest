package com.knowtest.lealtest.cadastro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.knowtest.lealtest.R;
import com.knowtest.lealtest.model.User;
import com.knowtest.lealtest.viewMoldel.CredentialViewModel;

public class CadastroActivity extends AppCompatActivity {

    private EditText name ;
    private  EditText email;
    private  EditText password;
    private Button buttonCadastrar;
    private  Button cleanButton;
    private FirebaseAuth autentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        this.getSupportActionBar().hide();

        name=findViewById(R.id.nome_cadastro);
        email=findViewById(R.id.email_cadastro);
        password=findViewById(R.id.senha_cadastro);
        buttonCadastrar = findViewById(R.id.botao_cadastrar);
        cleanButton=findViewById(R.id.botao_limpar);

        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome= name.getText().toString();
                String em= email.getText().toString();
                String pas=password.getText().toString();
                if ( !nome.isEmpty() ){
                    if ( !em.isEmpty() ){
                        if ( !pas.isEmpty() ){

                            User user = new User();
                            user.setName( nome );
                            user.setEmail( em );
                            user.setSenha( pas );
                            cadastrarUsuario(user);

                        }else {
                            Toast.makeText(CadastroActivity.this,
                                    "Preencha a senha!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(CadastroActivity.this,
                                "Preencha o email!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(CadastroActivity.this,
                            "Preencha o nome!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    public void cadastrarUsuario(User user){
        autentication= CredentialViewModel.Companion.getFirebaseAuth();
        autentication.createUserWithEmailAndPassword(
                user.getEmail(), user.getSenha()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(CadastroActivity.this,
                                    "sucesso ao casatrar usuarios",Toast.LENGTH_LONG).show();
                            finish();

                        }else{
                            String msg="";
                            try {
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                msg="cadastre uma senha mais forte";
                            } catch (FirebaseAuthInvalidCredentialsException e) {
                                msg="or favor, digite um e-mail válido";
                            } catch (FirebaseAuthUserCollisionException e) {
                                msg="Este conta já foi cadastrada";
                            } catch (Exception e) {
                                msg="erro ao cadastrar usuário "+e.getMessage();
                                e.printStackTrace();
                            }
                            Toast.makeText(CadastroActivity.this,
                                    msg,
                                    Toast.LENGTH_SHORT).show();

                        }

                    }
                }
        );
    }


    public void sairTelaCadastro(View v) {
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


    public void backButtonTelaCadastro(View view){
        finish();

    }


    public void clearCamps(View view) {

        name.getText().clear();
        email.getText().clear();
        password.getText().clear();


    }
}