package com.knowtest.lealtest.singletonInstances


import com.google.firebase.firestore.FirebaseFirestore

class FireStoreApi {

    companion object {

        private var db: FirebaseFirestore? = null

        //padrão sigleton
        fun getFirebaseFirestore(): FirebaseFirestore? {
            if (db == null) {
                db = FirebaseFirestore.getInstance()
            }
            return db
        }
    }


}