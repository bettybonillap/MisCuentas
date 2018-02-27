package com.example.beatr.miscuentas;

import java.util.ArrayList;

/**
 * Created by beatr on 26/02/2018.
 */

public class Dia {
    public int dia;
    public ArrayList<Transaccion> transacciones;
    public Dia(){
    }
    public Dia(int dia){
        this.dia=dia;
        transacciones=new ArrayList<Transaccion>();
    }
}
