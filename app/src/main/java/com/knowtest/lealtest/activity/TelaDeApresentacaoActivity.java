package com.knowtest.lealtest.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.knowtest.lealtest.R;
import com.knowtest.lealtest.singletonInstances.CredentialApi;
import com.knowtest.lealteste.Activity.model.Exercicio;
import com.squareup.picasso.Picasso;

public class TelaDeApresentacaoActivity extends AppCompatActivity {
    private TextView title;
    private TextView overView;
    private ImageView exiteButton;
    private ImageView backButton;
    private ImageView poster;
    private ImageView botaoSair;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_tela_de_apresentacao);
            this.getSupportActionBar().hide();
            Exercicio exercicio = (Exercicio) getIntent().getSerializableExtra("exercicio");


            String Arrstr []= exercicio.getObservacoes().split(" ");
            String firstWord=Arrstr[0];
            title = findViewById(R.id.titulo_tela_de_apresetacao);
            overView = findViewById(R.id.texto_descricao_tela_de_apresentacao);
            backButton = findViewById(R.id.voltar_tela_de_apresentacao);
            exiteButton = findViewById(R.id.sair_tela_de_apersentacao);
            poster = findViewById(R.id.foto_tela_de_apresentacao);
            Picasso.get().load(exercicio.getImagem().toString()).
                    placeholder(R.drawable.icone).error(R.drawable.icone).into(poster);
            title.setText(firstWord);
            overView.setText(exercicio.getObservacoes());
            botaoSair = findViewById(R.id.exit_button_tela_principal);


        }

        public void backButton(View view) {
            finish();

        }


        public void sairTelaDeApresentacao(View v) {
            //Cria o gerador do AlertDialog
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //define a mensagem
            builder.setMessage("Deseja Sair do Aplicativo?");
            builder.setCancelable(true);
            //define um botão pra sair
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                    finishAffinity();
                    System.exit(0);
                }
            });
            //define um botão pra cancelar.
            builder.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            //cria o AlertDialog
            AlertDialog alertDialog = builder.create();
            //Exibe
            alertDialog.show();

        }


        public void logOutTelaApresentacao(View v) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Fazer logou? precisará informar usuário e senha quando entrar.");
            builder.setCancelable(true);
            builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    FirebaseAuth auth = CredentialApi.Companion.getFirebaseAuth();
                    auth.signOut();
                    if (auth.getCurrentUser() == null) {
                        Log.d("usuario", "jorge candeias do nascimento");
                    }
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
