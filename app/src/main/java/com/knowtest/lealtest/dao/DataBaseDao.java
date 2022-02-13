package com.knowtest.lealtest.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.knowtest.lealtest.interfaces.IexercicioDao;
import com.knowtest.lealteste.Activity.model.Exercicio;

@Database(entities = {Exercicio.class}, version = 1, exportSchema = false)
public abstract class DataBaseDao extends RoomDatabase {
    public abstract IexercicioDao IexercicioDao();

}
