package com.knowtest.lealtest.viewModel;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.net.InetAddresses;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.knowtest.lealtest.model.Treino;
import com.knowtest.lealtest.singletonStances.DB;
import com.knowtest.lealtest.singletonStances.FireStoreApi;
import com.knowtest.lealtest.dao.DataBaseDao;
import com.knowtest.lealtest.interfaces.LealCalBack;
import com.knowtest.lealteste.Activity.model.Exercicio;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

public class FireStoreViewModel {
    private List<Exercicio> exercicios = new ArrayList<>();
    private List<Treino> treinos = new ArrayList<>();
    private List<Treino> treinoout = new ArrayList<>();
    private DataBaseDao db;

    private static FireStoreViewModel f;


    public static FireStoreViewModel fireStoreViewModel(Context context) {
        if (f == null) {
            return new FireStoreViewModel(context);
        } else {

            return f;
        }
    }


    private FireStoreViewModel(Context context) {
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
                    FirebaseFirestore fireStoredb = FireStoreApi.Companion.getFirebaseFirestore();
                    DocumentReference d = fireStoredb.document("EXERCICIO/" + ex.getId());
                    testetreino(d, ex);
                }


            }
        });
    }

    public void getTreinosInBack() {
        completReadData(new LealCalBack() {
            @Override
            public void onCallback(List<Map<String, Object>> mapList) {
                List<Exercicio> exerciciosLocal = new ArrayList<>();
                for (Map<String, Object> m : mapList) {
                    Treino t = new Treino();
                    t.setId(m.get("id").toString());
                    t.setData((Timestamp) m.get("data"));
                    t.setDescricao(m.get("descricao").toString());
                    //t.setExercicioStr((ArrayList<DocumentReference>) m.get("exercicios"));
                    t.setNome((Long) m.get("nome"));
                    t.setExercicios((ArrayList<Exercicio>) m.get("exercicios"));
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                           /* db.IexercicioDao().Insert(e);
                            exercicios.add(e);*/
                        }
                    }).start();
                }
            }
        });
    }


    public void readData(LealCalBack lealCallback) {
        FirebaseFirestore fireStoredb = FireStoreApi.Companion.getFirebaseFirestore();
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

    private void testetreino(DocumentReference d, Exercicio exec) {
        List<Exercicio> exercicios = new ArrayList<>();

        FirebaseFirestore fireStoredb = FireStoreApi.Companion.getFirebaseFirestore();
        // DocumentReference d = fireStoredb.document("EXERCICIO/" + e.getId());
        fireStoredb.collection("TREINO").whereArrayContains("exercicios", d)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Treino t = new Treino();
                                t.getExercicios().add(exec);
                                t.setId(document.getId().toString());
                                t.setData((Timestamp) document.get("data"));
                                t.setDescricao(document.get("descricao").toString());
                                t.setNome((Long) document.get("nome"));
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


    private void completReadData(LealCalBack lealCallback) {
        List<Exercicio> exercicios = new ArrayList<>();
        int i = 0;
        exercicios.addAll(this.exercicios);
        for (Exercicio e : exercicios) {
            FirebaseFirestore fireStoredb = FireStoreApi.Companion.getFirebaseFirestore();
            DocumentReference d = fireStoredb.document("EXERCICIO/" + e.getId());
            fireStoredb.collection("TREINO").whereArrayContains("exercicios", d)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<Map<String, Object>> eventList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Map<String, Object> m = new HashMap<String, Object>();
                                    // m.put("exerciciosStr", "EXERCICIO/" + e.getId());
                                    m.put("id", document.getId());
                                    m.put("easyLong", document.getLong("nome"));
                                    m.putAll(document.getData());
                                    eventList.add(m);
                                }

                                lealCallback.onCallback(eventList);
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                            }
                        }
                    });

        }
    }

    public void executeLoadExercicios() {
        Timer timer = new Timer();
        MyTimerTask myTask = new MyTimerTask();
        timer.schedule(myTask, 10000, 10000);
    }

    public List<Treino> getTreinos() {
        return treinos;
    }

    public List<Exercicio> getSe() {
        return exercicios;
    }


    public void getExerc() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                exercicios = getDb().IexercicioDao().getAll();
            }
        }).start();


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<Treino> getTreinoout() {
        List<Treino> localList = new ArrayList<>();
        List<Treino> r = new ArrayList<>();
        localList.addAll(treinoout);
        HashSet<Treino> hashSet = new HashSet(treinoout);
        treinoout.clear();
        treinoout.addAll(hashSet);

        for (Treino trExt : treinoout) {
            l1:
            for (Treino trInter : localList) {

                if ((trExt.equals(trInter))) {
                    if (!r.contains(trInter)){
                        r.add(trInter);
                        break l1;
                    }


                }
            }
        }
        return r;
    }

    class MyTimerTask extends TimerTask {
        public void run() {
            exercicios = getDb().IexercicioDao().getAll();
            //completReadData();
        }
    }

}
