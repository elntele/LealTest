package com.knowtest.lealteste.Activity.model


import androidx.room.*
import com.knowtest.lealtest.helper.UrlConverter
import java.net.URL

@Entity
class Exercicio (){
    @PrimaryKey
    var id: String=""
    @ColumnInfo (name= "nome_exercicio")
    var nome: Long? = null
    @ColumnInfo (name= "end_imagem")
    @TypeConverters(UrlConverter::class)
    var imagem: URL? = null
    @ColumnInfo (name= "observac")
    var observacoes: String? = null
}