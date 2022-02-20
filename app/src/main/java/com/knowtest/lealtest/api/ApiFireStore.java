package com.knowtest.lealtest.api;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.knowtest.lealtest.R;
import com.knowtest.lealtest.model.Treino;
import com.knowtest.lealtest.singletonInstances.DB;
import com.knowtest.lealtest.dao.DataBaseDao;
import com.knowtest.lealtest.singletonInstances.FireBaseStarangeApi;
import com.knowtest.lealtest.singletonInstances.FireStoreApi;
import com.knowtest.lealteste.Activity.model.Exercicio;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class ApiFireStore {
    private List<Exercicio> exercicios = new ArrayList<>();
    private List<Treino> treinos = new ArrayList<>();
    private List<Treino> treinoout = new ArrayList<>();
    private final String treino = "TREINO";
    private final String exercicio = "EXERCICIO";

    private DataBaseDao db;

    private static ApiFireStore f;

    private ApiFireStore(Context context) {
        db = DB.Companion.getDB(context);
    }

    public static synchronized ApiFireStore getIntance(Context context) {
        if (f == null) {
            f = new ApiFireStore(context);
        }
        return f;

    }

    public DataBaseDao getDb() {
        return db;
    }


    public void getInstancesFromApiFireBase() {
        FirebaseFirestore fireStoredb = FireStoreApi.Companion.getFirebaseFirestore();
        fireStoredb.collection(this.exercicio)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Exercicio> exeList = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Exercicio e = fillExercicioInstance(document);
                                exeList.add(e);
                                if (!exercicios.contains(e)) {
                                    exercicios.add(e);
                                }
                                insertExercicioInBank(e);
                                getIsntancesOfTreinoFronApiFireBase(e);
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void getIsntancesOfTreinoFronApiFireBase(Exercicio exec) {
        FirebaseFirestore fireStoredb = FireStoreApi.Companion.getFirebaseFirestore();
        DocumentReference d = fireStoredb.document(exercicio + "/" + exec.getId());
        fireStoredb.collection(treino).whereArrayContains("exercicios", d)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Treino t = fillTreinoInstance(document);
                                if (!treinoout.contains(t)) {
                                    treinoout.add(t);
                                }

                                insertTreinoInBank(t);
                                getUrlImages(t.getId(), exec);
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    private Exercicio fillExercicioInstance(QueryDocumentSnapshot d) {
        Exercicio e = new Exercicio();
        e.setId(d.getId());
        e.setNome(d.getLong("nome"));
        e.setObservacoes(d.get("observacoes").toString());
        return e;
    }

    private Treino fillTreinoInstance(QueryDocumentSnapshot d) {
        Treino t = new Treino();
        t.setId(d.getId());
        t.setNome(d.getLong("nome"));
        t.setData((Timestamp) d.get("data"));
        t.setDescricao(d.get("descricao").toString());
        t.setNome((Long) d.get("nome"));
        t.setStrinExe((ArrayList<DocumentReference>) d.get("exercicios"));
        insertTreinoInBank(t);
        return t;
    }


    public List<Treino> getTreinoout() {
        List<Treino> localList = new ArrayList<>();
        List<Treino> r = new ArrayList<>();
        localList.addAll(treinoout);
        HashSet set = new HashSet();
        set.addAll(treinoout);
        r.addAll(set);

        for (Treino t : r) {
            for (Exercicio e : exercicios) {

                for (DocumentReference d : t.getStrinExe()) {
                    String ls = d.getPath();
                    String[] l = ls.split("/");
                    String idDtr = l[1];
                    if (e.getId().equals(idDtr)) {
                        if (!t.getExercicios().contains(e)) {
                            t.getExercicios().add(e);
                        }

                    }
                }
            }
        }

        return r;
    }

    private void insertExercicioInBank(Exercicio e) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.IexercicioDao().Insert(e);

            }
        }).start();

    }

    private void insertTreinoInBank(Treino t) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                db.ItreinoDao().Insert(t);

            }
        }).start();

    }

    private void getUrlImages(String idTreino, Exercicio e) {
        FirebaseStorage storage = FireBaseStarangeApi.Companion.getStorangeRefe();
        StorageReference storageRef = storage.getReference();
        StorageReference folder = storageRef.child(idTreino + "/");
        StorageReference file = folder.child(e.getId().toString() + ".png");
        file.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    URL url = new URL(uri.toString());
                    e.setImagem(url);
                } catch (MalformedURLException malformedURLException) {
                    malformedURLException.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d("Falha", "in method getUrlImages " + exception.toString());
            }
        });
    }

    public void putTreino(Treino t) {
        FirebaseFirestore fireStoredb = FireStoreApi.Companion.getFirebaseFirestore();
        // Create a new user with a first and last name
        Map<String, Object> treino = new HashMap<>();
        treino.put("nome", t.getNome());
        treino.put("descricao", t.getDescricao());
        treino.put("data", t.getData());
        List<String> l = new ArrayList<>();
        for (Exercicio e : t.getExercicios()) {
            l.add(this.treino + "/" + e.getId());
        }
        treino.put("exercicios", l);

        fireStoredb.collection(this.treino)
                .add(treino)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        return;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        return;
                    }
                });
    }

    public void updateTreino(String id) {
        FirebaseFirestore fireStoredb = FireStoreApi.Companion.getFirebaseFirestore();
        // Create a new user with a first and last name
        Map<String, Object> treino = new HashMap<>();
        List<String> l = new ArrayList<>();
        l.add(this.exercicio+"/"+id);
    /*    l.add(this.treino + "/" + "teste");
        l.add(this.treino + "/" + "teste");
        l.add(this.treino + "/" + "teste");*/
        treino.put("exercicios", l);
        fireStoredb.collection(this.treino).document("OvQMQiHyfAiYS71RAIBM")
                .set(treino, SetOptions.merge());
    }

    public void putExercicio(Exercicio e) {
        FirebaseFirestore fireStoredb = FireStoreApi.Companion.getFirebaseFirestore();
        // Create a new user with a first and last name
        Map<String, Object> exercicio = new HashMap<>();
        exercicio.put("nome", e.getNome());
        exercicio.put("observacoes", e.getObservacoes());
        exercicio.put("imagem", e.getImagem());

        fireStoredb.collection(this.exercicio)
                .add(exercicio)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        return;
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        return;
                    }
                });
    }


}
