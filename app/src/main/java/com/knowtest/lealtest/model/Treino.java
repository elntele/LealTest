package com.knowtest.lealtest.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.knowtest.lealtest.helper.DateConverter;
import com.knowtest.lealtest.helper.ListExerciciosCoverter;
import com.knowtest.lealtest.helper.ListarRefCoverter;
import com.knowtest.lealteste.Activity.model.Exercicio;

import java.util.ArrayList;
import java.util.List;
@Entity
public class Treino {

    @PrimaryKey
    @NonNull
    String id="";
    @ColumnInfo(name = "nome_treino")
    Long nome=null;
    @ColumnInfo(name = "dec_treino")
    String descricao=null;
    @ColumnInfo(name = "data")
    @TypeConverters(DateConverter.class)
    Timestamp data=null;
    @ColumnInfo(name = "lis_exer")
    @TypeConverters(ListExerciciosCoverter.class)
    public ArrayList<Exercicio> exercicios;
    @TypeConverters(ListarRefCoverter.class)
    @ColumnInfo(name = "list_exe_ref")
    public ArrayList<DocumentReference> exercicioStr;

    public Treino() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getNome() {
        return nome;
    }

    public void setNome(Long nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public List<Exercicio> getExercicios() {
        return exercicios;
    }



    public List<DocumentReference> getExercicioStr() {
        return exercicioStr;
    }

    public void setExercicios(ArrayList<Exercicio> exercicios) {
        this.exercicios = exercicios;
    }

    public void setExercicioStr(ArrayList<DocumentReference> exercicioStr) {
        this.exercicioStr = exercicioStr;
    }
}
