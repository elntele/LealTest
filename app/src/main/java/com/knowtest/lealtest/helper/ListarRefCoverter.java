package com.knowtest.lealtest.helper;

import androidx.room.TypeConverter;

import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.DocumentReference;
import com.google.gson.Gson;
import com.knowtest.lealteste.Activity.model.Exercicio;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ListarRefCoverter {

    @TypeConverter
    public static ArrayList<DocumentReference> fromString(String value) {
        Type listType = new TypeToken<ArrayList<DocumentReference>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<DocumentReference> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
