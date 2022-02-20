package com.knowtest.lealtest;


import com.google.firebase.Timestamp;
import com.knowtest.lealtest.activity.MainActivity;
import com.knowtest.lealtest.api.ApiFireStore;
import com.knowtest.lealtest.model.Treino;
import com.knowtest.lealteste.Activity.model.Exercicio;

import org.junit.Test;

import java.util.Date;


public class TestApiFirteStore {
    private  ApiFireStore f;
    @Test
    public void insertTreino(){
       f= f.getIntance(MainActivity.a);
       Treino t = new Treino();
       t.setData(Timestamp.now());
       t.setNome(101l);
       t.setDescricao("Correr: correr é bom para emagrecer, tonificar as coxas, " +
               "manter a forma e trabalhar a resistência muscular. Mas a corrida não é" +
               " só sobre ficar com o corpo em forma. Praticar um esporte como a corrida " +
               "também é uma das melhores coisas que você pode fazer pelo seu cérebro em termos" +
               " de humor, memória e aprendizado.");
       f.putTreino(t);

    }
    public Long toTimestamp(String strDate) {
        long millis = System.currentTimeMillis();

        Timestamp timestamp= new Timestamp(0,0);
        return millis;
    }
    @Test
    public void inserteExercicio(){
        f= f.getIntance(MainActivity.a);
        Exercicio e = new Exercicio();
        e.setNome(10l);
        e.setObservacoes("Uma revisão de vários estudos sobre exercícios abdominais concluiu" +
                " que eles melhoram a flexibilidade e a força muscular. Em cães, por exemplo," +
                " a atividade também ajuda na distribuição de nutrientes para os discos da " +
                "espinha dorsal, o que previne a rigidez. Mas chegar à barriga tanquinho" +
                " é um trabalho e tanto.");
        f.putExercicio(e);

    }
    @Test
    public void updatTreino(){
        f= f.getIntance(MainActivity.a);
        f.updateTreino("mandei essa");
    }


}
