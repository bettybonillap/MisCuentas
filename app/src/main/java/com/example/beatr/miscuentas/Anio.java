package com.example.beatr.miscuentas;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by beatr on 26/02/2018.
 */

public class Anio implements Parcelable {
    public int anio;
    public ArrayList<Mes> meses;

    public Anio(){}

    public Anio(int anio){
        this.anio=anio;
        meses=new ArrayList<Mes>();
    }

    // Required method to write to Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(anio);
        dest.writeList(meses);
    }

    /**
     * Constructs a Question from a Parcel
     * @param parcel Source Parcel
     */
    public Anio(Parcel parcel) {
        this.anio = parcel.readInt();
        this.meses = parcel.readArrayList(Mes.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Method to recreate a Question from a Parcel
    public static Creator<Anio> CREATOR = new Creator<Anio>() {

        @Override
        public Anio createFromParcel(Parcel source) {
            return new Anio(source);
        }

        @Override
        public Anio[] newArray(int size) {
            return new Anio[size];
        }

    };
}
