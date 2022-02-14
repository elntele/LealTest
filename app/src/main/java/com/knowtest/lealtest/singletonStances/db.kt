package com.knowtest.lealtest.singletonStances

import android.content.Context
import androidx.room.Room
import com.knowtest.lealtest.dao.DataBaseDao

class DB() {

    companion object {
        private var db: DataBaseDao? = null

        //padrão sigleton
        fun getDB(context: Context?): DataBaseDao? {
            if (db == null) {
                db = context?.let {
                    Room.databaseBuilder<DataBaseDao?>(
                        it,
                        DataBaseDao::class.java,
                        "MeuBD"
                    ).build()
                }
            }
            return db
        }
    }

}