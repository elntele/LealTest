package com.knowtest.lealtest.modeView;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.knowtest.lealtest.api.FireStoreApi;
import com.knowtest.lealtest.interfaces.LealCalBack;
import com.knowtest.lealteste.Activity.model.Treino;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireStoreViewModel {

    public FireStoreViewModel() {
    }

    public List<Treino> getTreinos(){
        List <Treino> l = new ArrayList<>();
        readData(new LealCalBack() {
            @Override
            public void onCallback(List<Map<String, Object>> eventList) {
                Treino t = new Treino();
               // t.setId(eventList.get("id"));
                Log.d("TAG", eventList.toString());

            }
        });
        return null;
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
                                //e.setId(doc.getId());
                                eventList.add(m);
                            }
                            lealCallback.onCallback(eventList);
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }

                });
    }


}
