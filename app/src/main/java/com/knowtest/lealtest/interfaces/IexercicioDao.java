package com.knowtest.lealtest.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.knowtest.lealteste.Activity.model.Exercicio;

import java.util.List;
@Dao
public interface IexercicioDao {
    @Query("Select * from exercicio")
    List<Exercicio> getAll();
    @Query("Select * from exercicio where id like :id limit 1")
    Exercicio findByid(String id);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert (Exercicio exercicio);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Insert (List<Exercicio> exercicios);
    @Update
    void update(Exercicio exercicio);
    @Delete
    void delete(Exercicio exercicio);

}
