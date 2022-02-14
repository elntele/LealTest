package com.knowtest.lealtest.dao;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.knowtest.lealtest.interfacesDao.IexercicioDao;
import com.knowtest.lealtest.interfacesDao.ItreinoDao;
import com.knowtest.lealtest.model.Treino;
import com.knowtest.lealteste.Activity.model.Exercicio;

@Database(entities = {Exercicio.class, Treino.class}, version = 1, exportSchema = false)
public abstract class DataBaseDao extends RoomDatabase {
    public abstract IexercicioDao IexercicioDao();
    public abstract ItreinoDao ItreinoDao();

}
