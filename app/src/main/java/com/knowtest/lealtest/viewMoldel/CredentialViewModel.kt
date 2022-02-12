package com.knowtest.lealtest.viewMoldel

import com.google.firebase.auth.FirebaseAuth

// kotlin
class CredentialViewModel() {
    companion object {
        private var firebaseAuth: FirebaseAuth? = null
        //padrão sigleton
        fun getFirebaseAuth(): FirebaseAuth? {
            if (firebaseAuth == null) {
                firebaseAuth = FirebaseAuth.getInstance()
            }
            return firebaseAuth
        }
    }




}