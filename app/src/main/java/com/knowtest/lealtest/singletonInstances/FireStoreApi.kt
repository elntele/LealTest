package com.knowtest.lealtest.singletonInstances


import com.google.firebase.firestore.FirebaseFirestore

class FireStoreApi {

    companion object {

        private var firebaseFirestore: FirebaseFirestore? = null

        //padrão sigleton
        fun getFirebaseFirestore(): FirebaseFirestore? {
            if (firebaseFirestore == null) {
                firebaseFirestore = FirebaseFirestore.getInstance()
            }
            return firebaseFirestore
        }
    }


}