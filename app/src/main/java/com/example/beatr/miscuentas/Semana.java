package com.example.beatr.miscuentas;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by beatr on 26/02/2018.
 */

public class Semana implements Parcelable {
    public int semana;
    public ArrayList<Dia> dias;
    public Semana(){
    }
    public Semana(int semana){
        this.semana=semana;
        dias=new ArrayList<Dia>();
    }

    // Required method to write to Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(semana);
        dest.writeList(dias);
    }

    /**
     * Constructs a Question from a Parcel
     * @param parcel Source Parcel
     */
    public Semana(Parcel parcel) {
        this.semana = parcel.readInt();
        this.dias = parcel.readArrayList(Dia.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }


    // Method to recreate a Question from a Parcel
    public static Parcelable.Creator<Semana> CREATOR = new Parcelable.Creator<Semana>() {

        @Override
        public Semana createFromParcel(Parcel source) {
            return new Semana(source);
        }

        @Override
        public Semana[] newArray(int size) {
            return new Semana[size];
        }

    };
}
