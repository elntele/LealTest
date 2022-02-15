package com.knowtest.lealtest.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.knowtest.lealtest.R;
import com.knowtest.lealtest.adapter.TelaDeExercicioAdapter;
import com.knowtest.lealtest.helper.ClearCache;
import com.knowtest.lealtest.helper.RecyclerItemClickListener;
import com.knowtest.lealtest.singletonInstances.CredentialApi;
import com.knowtest.lealteste.Activity.model.Exercicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TelaDeExercicioActivity extends AppCompatActivity {

    private RecyclerView recycleViewTelaExercicios;
    private TelaDeExercicioAdapter adapter;
    private List<Exercicio> exercicios = new ArrayList();
    private SearchView searchView;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.getSupportActionBar().hide();
        ArrayList<Exercicio> data;
        data = getIntent().getExtras().getParcelableArrayList("exercicios");
        exercicios = data;
        setContentView(R.layout.activity_tela_de_exercicio);
        searchView = findViewById(R.id.search_tela_exerc);
        recycleViewTelaExercicios = findViewById(R.id.recicler_lista_file_exerc);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        final TelaDeExercicioAdapter adapter = new TelaDeExercicioAdapter(exercicios, getApplicationContext());
        recycleViewTelaExercicios.setLayoutManager(layoutManager);
        recycleViewTelaExercicios.setAdapter(adapter);
        this.adapter = adapter;

        recycleViewTelaExercicios.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recycleViewTelaExercicios, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getApplicationContext(), TelaDeApresentacaoActivity.class);
                        intent.putExtra("exercicio", (Serializable) exercicios.get(position));
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                        Intent intent = new Intent(getApplicationContext(), TelaDeApresentacaoActivity.class);
                        intent.putExtra("exercicio", (Serializable) exercicios.get(position));
                        startActivity(intent);
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                })

        );




        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                return true;
            }
        });

    }


    public void backButtonTelaExc(View view) {
        finish();

    }


    public void sairTelaInicial(View v) {
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


    public void logOutTelaInicial(View v) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Fazer logou? precisará informar usuário e senha quando entrar.");
        builder.setCancelable(true);
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth auth = CredentialApi.Companion.getFirebaseAuth();
                auth.signOut();
                ClearCache.deleteCache(getApplicationContext());
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