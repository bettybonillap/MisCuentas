package com.example.beatr.miscuentas;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by beatr on 26/02/2018.
 */

public class Transaccion implements Parcelable {
    public String fecha;
    public String concepto;
    public double cantidad;
    public boolean tipo; //true ingreso, 0 egreso
    public int anio,mes,dia;

    public Transaccion(){}

    public Transaccion(String concepto, double cantidad,boolean tipo, String fecha){
        this.concepto=concepto;
        this.tipo=tipo;
        //if(!tipo){
           //cantidad=-cantidad;
        //}
        this.cantidad=cantidad;
        this.fecha=fecha;
        this.anio = Integer.parseInt(fecha.substring(fecha.lastIndexOf('/') + 1, fecha.length()));
        this.mes= Integer.parseInt(fecha.substring(fecha.indexOf('/') + 1, fecha.lastIndexOf('/')));
        this.dia = Integer.parseInt(fecha.substring(0, fecha.indexOf('/')));
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
