package com.knowtest.lealteste.Activity.model


import android.os.Parcel
import android.os.Parcelable
import androidx.room.*
import com.google.gson.annotations.Expose
import com.knowtest.lealtest.helper.UrlConverter
import java.net.MalformedURLException
import java.net.URL

@Entity
class Exercicio (): Parcelable, java.io.Serializable {
    @PrimaryKey
    @Expose
    var id: String=""
    @ColumnInfo (name= "nome_exercicio")
    @Expose
    var nome: Long? = null
    @ColumnInfo (name= "end_imagem")
    @TypeConverters(UrlConverter::class)
    @Expose
    var imagem: URL? = null
    @ColumnInfo (name= "observac")
    @Expose
    var observacoes: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString().toString()
        nome = parcel.readValue(Long::class.java.classLoader) as? Long
        observacoes = parcel.readString()
       var im =parcel.readString().toString()
        try {
            val url = URL(im)
            imagem = url
        } catch (malformedURLException: MalformedURLException) {
            malformedURLException.printStackTrace()
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeValue(nome)
        parcel.writeString(observacoes)
        parcel.writeString(imagem.toString())

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Exercicio> {
        override fun createFromParcel(parcel: Parcel): Exercicio {
            return Exercicio(parcel)
        }

        override fun newArray(size: Int): Array<Exercicio?> {
            return arrayOfNulls(size)
        }
    }


}