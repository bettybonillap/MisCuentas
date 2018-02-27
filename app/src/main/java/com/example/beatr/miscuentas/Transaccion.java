package com.example.beatr.miscuentas;

/**
 * Created by beatr on 26/02/2018.
 */

public class Transaccion {
    public String concepto;
    public double cantidad;
    public boolean tipo; //true ingreso, 0 egreso
    public Transaccion(String concepto, double cantidad,boolean tipo){
        this.concepto=concepto;
        this.cantidad=cantidad;
        this.tipo=tipo;
    }
}
