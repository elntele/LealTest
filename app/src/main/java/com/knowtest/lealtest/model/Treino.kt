package com.knowtest.lealteste.Activity.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference
import com.knowtest.lealtest.helper.ListCoverter
import com.knowtest.lealtest.helper.UrlConverter

@Entity
class Treino (){
    @PrimaryKey
    var id: String=""
    @ColumnInfo(name= "nome_treino")
    var nome: Long? = null
    @ColumnInfo (name= "dec_treino")
    var descricao: String? = null
    @ColumnInfo (name= "data")
    var  data: Timestamp?=null
    @ColumnInfo (name= "end_imagem")
    @TypeConverters(ListCoverter::class)
    var exercicio:MutableList<Exercicio>?=null
    @TypeConverters(UrlConverter::class)
    var exercicioStr:MutableList<DocumentReference>?=null


}