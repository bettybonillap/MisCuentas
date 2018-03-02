package com.example.beatr.miscuentas;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by beatr on 26/02/2018.
 */

public class Mes implements Parcelable{
    public int mes;
    public ArrayList<Semana> semanas;
    public Mes(){
    }
    public Mes(int mes){
        this.mes=mes;
        semanas=new ArrayList<Semana>();
    }

    // Required method to write to Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mes);
        dest.writeList(semanas);
    }

    /**
     * Constructs a Question from a Parcel
     * @param parcel Source Parcel
     */
    public Mes(Parcel parcel) {
        this.mes = parcel.readInt();
        this.semanas = parcel.readArrayList(Semana.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }


    // Method to recreate a Question from a Parcel
    public static Parcelable.Creator<Mes> CREATOR = new Parcelable.Creator<Mes>() {

        @Override
        public Mes createFromParcel(Parcel source) {
            return new Mes(source);
        }

        @Override
        public Mes[] newArray(int size) {
            return new Mes[size];
        }

    };
}
