package com.knowtest.lealtest.modeView;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.knowtest.lealtest.api.FireStoreApi;
import com.knowtest.lealtest.dao.DataBaseDao;
import com.knowtest.lealtest.interfaces.LealCalBack;
import com.knowtest.lealteste.Activity.model.Exercicio;
import com.knowtest.lealteste.Activity.model.Treino;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireStoreViewModel {
    private List<Exercicio> exercicios = new ArrayList<>();
    private DataBaseDao db;
    public FireStoreViewModel(Context context) {
        db = Room.databaseBuilder(context, DataBaseDao.class, "MeuBD").build();
    }

    public DataBaseDao getDb() {
        return db;
    }

    public  void getExerciciosInBack(){

        readData(new LealCalBack() {
            @Override
            public void onCallback(List<Map<String, Object>> mapList) {
                Exercicio e = new Exercicio();
                for (Map<String, Object> m:mapList ){
                    e.setId(m.get("id").toString());
                    e.setObservacoes(m.get("observacoes").toString());
                    e.setNome( (Long)m.get("easyLong"));
                    try {
                        URL url = new URL(m.get("imagem").toString());
                        e.setImagem(url);
                    } catch (MalformedURLException malformedURLException) {
                        malformedURLException.printStackTrace();
                    }

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            db.IexercicioDao().Insert(e);

                        }
                    }).start();
                    exercicios.add(e);

                }

               // t.setId(eventList.get("id"));
                Log.d("TAG", mapList.toString());

            }
        });

    }


    public void readData(LealCalBack lealCallback) {
        FirebaseFirestore db = FireStoreApi.Companion.getFirebaseFirestore();
        db.collection("EXERCICIO")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Map<String, Object>> eventList = new ArrayList<>();

                            for(QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> m = new HashMap<String, Object>();
                                m.put("id",document.getId());
                                m.put("easyLong", document.getLong("nome"));
                                m.putAll(document.getData());
                                eventList.add(m);
                            }
                            lealCallback.onCallback(eventList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }

                });
    }

    public List<Exercicio> getExercicios() {
        return exercicios;

    }
}
