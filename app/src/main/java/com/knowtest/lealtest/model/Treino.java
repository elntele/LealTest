package com.knowtest.lealtest.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.gson.annotations.Expose;
import com.knowtest.lealtest.helper.DateConverter;
import com.knowtest.lealtest.helper.ListExerciciosCoverter;
import com.knowtest.lealtest.helper.ListarRefCoverter;
import com.knowtest.lealteste.Activity.model.Exercicio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Treino implements Parcelable, Serializable {

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
    @Expose
    @ColumnInfo(name = "lis_exer")
    @TypeConverters(ListExerciciosCoverter.class)
    public ArrayList<Exercicio> exercicios = new ArrayList<>();
    @Ignore
    public ArrayList<DocumentReference> strinExe = new ArrayList<>();

    public Treino() {
    }

    protected Treino(Parcel in) {
        id = in.readString();
        if (in.readByte() == 0) {
            nome = null;
        } else {
            nome = in.readLong();
        }
        descricao = in.readString();
        data = in.readParcelable(Timestamp.class.getClassLoader());
        exercicios = in.createTypedArrayList(Exercicio.CREATOR);
    }

    public static final Creator<Treino> CREATOR = new Creator<Treino>() {
        @Override
        public Treino createFromParcel(Parcel in) {
            return new Treino(in);
        }

        @Override
        public Treino[] newArray(int size) {
            return new Treino[size];
        }
    };

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

    public ArrayList<DocumentReference> getStrinExe() {
        return strinExe;
    }

    public void setStrinExe(ArrayList<DocumentReference> strinExe) {
        this.strinExe = strinExe;
    }

    public void setExercicios(ArrayList<Exercicio> exercicios) {
        this.exercicios = exercicios;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        if (nome == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(nome);
        }
        parcel.writeString(descricao);
        parcel.writeParcelable(data, i);
        parcel.writeTypedList(exercicios);
    }



    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
            o = (Treino) o;
        if  ( this.getId().equals(((Treino) o).getId())){
            return true;
        }else{
            return false;
        }


    }

}
