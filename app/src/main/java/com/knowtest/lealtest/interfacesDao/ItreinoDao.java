package com.knowtest.lealtest.interfacesDao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.knowtest.lealtest.model.Treino;

import java.util.List;

@Dao
public interface ItreinoDao {
    @Query("Select * from exercicio")
    List<Treino> getAll();
    @Query("Select * from exercicio where id like :id limit 1")
    Treino findByid(String id);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert (Treino treino);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert (List<Treino> treinos);
    @Update
    void update(Treino treino);
    @Delete
    void delete(Treino treino);
}
