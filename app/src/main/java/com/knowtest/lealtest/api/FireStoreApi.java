package com.knowtest.lealtest.api;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.knowtest.lealtest.model.Treino;
import com.knowtest.lealtest.singletonInstances.DB;
import com.knowtest.lealtest.dao.DataBaseDao;
import com.knowtest.lealtest.interfaceCallBack.LealCalBack;
import com.knowtest.lealteste.Activity.model.Exercicio;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireStoreApi {
    private List<Exercicio> exercicios = new ArrayList<>();
    private List<Treino> treinos = new ArrayList<>();
    private List<Treino> treinoout = new ArrayList<>();
    private final String treino = "TREINO";
    private final String exercicio = "EXERCICIO";

    private DataBaseDao db;

    private static FireStoreApi f;


    public static FireStoreApi fireStoreViewModel(Context context) {
        if (f == null) {
            return new FireStoreApi(context);
        } else {

            return f;
        }
    }


    private FireStoreApi(Context context) {
        db = DB.Companion.getDB(context);
    }

    public DataBaseDao getDb() {
        return db;
    }

    public void getExerciciosInBack() {
        readData(new LealCalBack() {
            @Override
            public void onCallback(List<Map<String, Object>> mapList) {
                List<Exercicio> exerciciosLocal = new ArrayList<>();
                for (Map<String, Object> m : mapList) {
                    Exercicio e = new Exercicio();
                    e.setId(m.get("id").toString());
                    e.setObservacoes(m.get("observacoes").toString());
                    e.setNome((Long) m.get("easyLong"));
                    try {
                        URL url = new URL(m.get("imagem").toString());
                        e.setImagem(url);
                    } catch (MalformedURLException malformedURLException) {
                        malformedURLException.printStackTrace();
                    }
                    exerciciosLocal.add(e);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            db.IexercicioDao().Insert(e);
                            exercicios.add(e);
                        }
                    }).start();
                }
                for (Exercicio ex : exerciciosLocal) {
                    FirebaseFirestore fireStoredb = com.knowtest.lealtest.singletonInstances.FireStoreApi.Companion.getFirebaseFirestore();
                    DocumentReference d = fireStoredb.document("EXERCICIO/" + ex.getId());
                    preenchListaTreino(d, ex);
                }


            }
        });
    }


    public void readData(LealCalBack lealCallback) {
        FirebaseFirestore fireStoredb = com.knowtest.lealtest.singletonInstances.FireStoreApi.Companion.getFirebaseFirestore();
        fireStoredb.collection("EXERCICIO")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Map<String, Object>> eventList = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> m = new HashMap<String, Object>();
                                m.put("id", document.getId());
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

    private void preenchListaTreino(DocumentReference d, Exercicio exec) {
        List<Exercicio> exercicios = new ArrayList<>();

        FirebaseFirestore fireStoredb = com.knowtest.lealtest.singletonInstances.FireStoreApi.Companion.getFirebaseFirestore();
        // DocumentReference d = fireStoredb.document("EXERCICIO/" + e.getId());
        fireStoredb.collection("TREINO").whereArrayContains("exercicios", d)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Treino t = new Treino();
                                //t.getExercicios().add(exec);
                                t.setId(document.getId().toString());
                                t.setData((Timestamp) document.get("data"));
                                t.setDescricao(document.get("descricao").toString());
                                t.setNome((Long) document.get("nome"));
                                t.setStrinExe((ArrayList<DocumentReference>) document.get("exercicios"));
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Treino tFromBank = new Treino();
                                        tFromBank = db.ItreinoDao().findByid(t.getId());
                                        treinoout.add(t);

                                        if (tFromBank == null) {
                                            db.ItreinoDao().Insert(t);
                                        } else {
                                            if (!treinoout.contains(t)) {
                                                treinoout.add(t);
                                            }
                                        }
                                    }
                                }).start();
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    }


    public List<Treino> getTreinoout() {
        List<Treino> localList = new ArrayList<>();
        List<Treino> r = new ArrayList<>();
        localList.addAll(treinoout);
         for (Treino trExt : treinoout) {
             l1:
             for (Treino trInter : localList) {
                 if ((trExt.equals(trInter))) {
                     if (!r.contains(trInter)){
                         r.add(trInter);
                         localList.remove(trInter);
                         break l1;
                     }

                 }
             }

         }
         List<Treino> retorno = new ArrayList<>();
         for (Treino t:r){
             for (Exercicio e: exercicios){
                for (DocumentReference d: t.getStrinExe()){
                    String ls= d.getPath();
                    String [] l  = ls.split("/");
                    String idDtr= l[1];
                    if(e.getId().equals(idDtr)){
                        t.getExercicios().add(e);
                    }
                }
             }
         }

        return r;
    }


}
