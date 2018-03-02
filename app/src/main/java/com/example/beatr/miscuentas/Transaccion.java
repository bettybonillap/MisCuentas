package com.example.beatr.miscuentas;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by beatr on 26/02/2018.
 */

public class Transaccion implements Parcelable {
    public String concepto;
    public double cantidad;
    public boolean tipo; //true ingreso, 0 egreso

    public Transaccion(){}

    public Transaccion(String concepto, double cantidad,boolean tipo){
        this.concepto=concepto;
        this.cantidad=cantidad;
        this.tipo=tipo;
    }

    // Required method to write to Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(concepto);
        dest.writeDouble(cantidad);
        dest.writeValue(tipo);
    }

    /**
     * Constructs a Question from a Parcel
     * @param parcel Source Parcel
     */
    public Transaccion(Parcel parcel) {
        this.concepto = parcel.readString();
        this.cantidad = parcel.readDouble();
        this.tipo = (Boolean) parcel.readValue( null );
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // Method to recreate a Question from a Parcel
    public static Parcelable.Creator<Transaccion> CREATOR = new Parcelable.Creator<Transaccion>() {

        @Override
        public Transaccion createFromParcel(Parcel source) {
            return new Transaccion(source);
        }

        @Override
        public Transaccion[] newArray(int size) {
            return new Transaccion[size];
        }

    };
}
