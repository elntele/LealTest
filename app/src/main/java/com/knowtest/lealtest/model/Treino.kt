package com.knowtest.lealteste.Activity.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentReference


class Treino (){
    var id: String?=null
    var nome: Long? = null
    var descricao: String? = null
    lateinit var  data: Timestamp
    var exercicio:MutableList<Exercicio>?=null
    var exercicioStr:MutableList<DocumentReference>?=null


}