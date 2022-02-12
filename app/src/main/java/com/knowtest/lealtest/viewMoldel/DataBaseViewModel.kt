package com.knowtest.lealtest.viewMoldel


import com.google.firebase.firestore.FirebaseFirestore

class DataBaseViewModel {

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