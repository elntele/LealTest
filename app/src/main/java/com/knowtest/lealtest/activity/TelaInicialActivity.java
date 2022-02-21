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
import com.knowtest.lealtest.adapter.TelaIncialAdapter;
import com.knowtest.lealtest.helper.ClearCache;
import com.knowtest.lealtest.helper.RecyclerItemClickListener;
import com.knowtest.lealtest.model.Treino;
import com.knowtest.lealtest.singletonInstances.CredentialApi;
import com.knowtest.lealteste.Activity.model.Exercicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TelaInicialActivity extends AppCompatActivity {

    private RecyclerView recycleViewTelaInicial;
    private TelaIncialAdapter adapter;
    private List<Treino> treinos = new ArrayList();
    private SearchView searchView;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.getSupportActionBar().hide();
        ArrayList<Treino> data;
        data = getIntent().getExtras().getParcelableArrayList("array");
        treinos =data;
        setContentView(R.layout.activity_tela_inicial);
        searchView = findViewById(R.id.search_tela_inicial);
        recycleViewTelaInicial = findViewById(R.id.recicler_lista_file);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        final TelaIncialAdapter adapter = new TelaIncialAdapter(treinos, getApplicationContext(), this);
        recycleViewTelaInicial.setLayoutManager(layoutManager);
        recycleViewTelaInicial.setAdapter(adapter);
        this.adapter=adapter;

        recycleViewTelaInicial.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), recycleViewTelaInicial, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getApplicationContext(), TelaDeExercicioActivity.class);
                        intent.putParcelableArrayListExtra("exercicios", (ArrayList<Exercicio>) treinos.get(position).exercicios);
                        intent.putExtra("idTreino", treinos.get(position).getId());
                        startActivity(intent);

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                        Intent intent = new Intent(getApplicationContext(), TelaDeExercicioActivity.class);
                        intent.putParcelableArrayListExtra("exercicios", (ArrayList<Exercicio>) treinos.get(position).exercicios);
                        intent.putExtra("idTreino", treinos.get(position).getId());
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

    public void CadastrarTreino(View view){
        Intent intent = new Intent(getApplicationContext(), AddTreinoActivity.class);
        startActivity(intent);
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