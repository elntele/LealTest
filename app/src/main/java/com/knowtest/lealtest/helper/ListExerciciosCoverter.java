package com.knowtest.lealtest.helper;

import androidx.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.knowtest.lealteste.Activity.model.Exercicio;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListExerciciosCoverter {

    @TypeConverter
    public static ArrayList<Exercicio> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Exercicio>>() {
        }.getType();
        ArrayList<Exercicio> e = new Gson().fromJson(value, listType);
        return e;
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<Exercicio> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
