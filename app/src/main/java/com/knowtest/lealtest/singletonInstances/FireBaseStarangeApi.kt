package com.knowtest.lealtest.singletonInstances

import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference




class FireBaseStarangeApi {

    companion object {
        private var storageRef: StorageReference? = null
        private var storageRefUrl: StorageReference? = null

        //padrão sigleton
        fun getStorangeRefe(): StorageReference? {
            if (storageRef == null) {
                storageRef= FirebaseStorage.getInstance().getReference()
            }
            return storageRef
        }

        fun getStorangeRefeFromUrl(string : String?): StorageReference? {
            if (storageRefUrl == null) {
                storageRefUrl=
                    string?.let { FirebaseStorage.getInstance().getReferenceFromUrl(it) };
            }
            return storageRefUrl
        }



    }
}