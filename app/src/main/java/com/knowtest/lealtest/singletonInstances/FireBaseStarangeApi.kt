package com.knowtest.lealtest.singletonInstances

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference




class FireBaseStarangeApi {

    companion object {
        private var storage: FirebaseStorage?=null;

        //padrão sigleton
        fun getStorangeRefe(): FirebaseStorage? {
            if (storage == null) {
                 storage = FirebaseStorage.getInstance();
            }
            return storage
        }
    }
}