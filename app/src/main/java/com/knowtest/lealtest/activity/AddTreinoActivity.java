package com.knowtest.lealtest.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.protobuf.Internal;
import com.knowtest.lealtest.R;
import com.knowtest.lealtest.api.ApiFireStore;
import com.knowtest.lealtest.model.Treino;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

public class AddTreinoActivity extends AppCompatActivity {

    private EditText name;
    private CalendarView dataTreino;
    private EditText descTreino;
    private Button buttonCadastrar;
    private Button cleanButton;
    private long l;
    private ApiFireStore f = ApiFireStore.getIntance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_treino);
        this.getSupportActionBar().hide();
        name = findViewById(R.id.nome_treino);
        dataTreino = findViewById(R.id.data_treino);
        l = dataTreino.getDate();
        descTreino = findViewById(R.id.desc_treino);
        buttonCadastrar = findViewById(R.id.botao_cadastrar);
        cleanButton = findViewById(R.id.botao_limpar);

        cadatrarTreino();

    }

    private void cadatrarTreino() {

        final long[] miliSegundos = new long[1];
        miliSegundos[0] = l = dataTreino.getDate();

        dataTreino.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            //show the selected date as a toast
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {
                Toast.makeText(getApplicationContext(), day + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
                Calendar c = Calendar.getInstance();
                c.set(year, month, day);
                Date date = c.getTime();
                //segundos[0] = date.getSeconds();
                // miliSegundos[0]=date.getTime();
                miliSegundos[0] = c.getTimeInMillis(); //this is what you want to use later
            }
        });
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = name.getText().toString();
                String dt = descTreino.getText().toString();
                if (!nome.isEmpty()) {
                    if (!(miliSegundos[0] == l)) {
                        if (!dt.isEmpty()) {

                            Treino treino = new Treino();
                            treino.setNome(Long.parseLong(nome));
                            treino.setDescricao(dt);
                            long seg = miliSegundos[0] / 1000;
                            Timestamp timestamp = new Timestamp(seg, 0);
                            treino.setData(timestamp);
                            f.putTreino(treino);
                            clearCamps();
                        } else {
                            Toast.makeText(AddTreinoActivity.this,
                                    "Preencha a descrição!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        dataDeHoje(nome, miliSegundos[0], dt);
                    }
                } else {
                    Toast.makeText(AddTreinoActivity.this,
                            "Preencha o nome!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    public void cadastrarUsuario(Treino user) {

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

    public void dataDeHoje(String nome, long miliSegundos, String desc) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("a data selecionada é a de hoje confirmar ?");
        builder.setCancelable(true);
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Treino treino = new Treino();
                treino.setNome(Long.getLong(nome));
                treino.setDescricao(desc);
                long seg = miliSegundos / 1000;
                Timestamp timestamp = new Timestamp(seg, 0);
                treino.setData(timestamp);
                f.putTreino(treino);
                clearCamps();
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


    public void backButtonTelaCadastro(View view) {
        finish();

    }
    private void clearCamps() {
        name.getText().clear();
        dataTreino.setDate(l);
        descTreino.getText().clear();
        Toast.makeText(AddTreinoActivity.this,
                "Treino cadastrado!",
                Toast.LENGTH_SHORT).show();
        f.getInstancesFromApiFireBase();
    }

    public void clearCamps(View view) {
        name.getText().clear();
        dataTreino.setDate(l);
        descTreino.getText().clear();
    }
}