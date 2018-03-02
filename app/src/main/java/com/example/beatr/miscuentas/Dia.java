package com.example.beatr.miscuentas;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by beatr on 26/02/2018.
 */

public class Dia implements Parcelable {
    public int dia;
    public ArrayList<Transaccion> transacciones;

    public Dia(){
    }

    public Dia(int dia){
        this.dia=dia;
        transacciones=new ArrayList<Transaccion>();
    }

    // Required method to write to Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(dia);
        dest.writeList(transacciones);
    }

    /**
     * Constructs a Question from a Parcel
     * @param parcel Source Parcel
     */
    public Dia(Parcel parcel) {
        this.dia = parcel.readInt();
        this.transacciones = parcel.readArrayList(Transaccion.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }


    // Method to recreate a Question from a Parcel
    public static Parcelable.Creator<Dia> CREATOR = new Parcelable.Creator<Dia>() {

        @Override
        public Dia createFromParcel(Parcel source) {
            return new Dia(source);
        }

        @Override
        public Dia[] newArray(int size) {
            return new Dia[size];
        }

    };
}
