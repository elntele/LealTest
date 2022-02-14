package com.knowtest.lealtest.singletonInstances

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