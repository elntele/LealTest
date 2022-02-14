package com.knowtest.lealtest.singletonStances

import com.google.firebase.auth.FirebaseAuth

// kotlin
class CredentialApi() {
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