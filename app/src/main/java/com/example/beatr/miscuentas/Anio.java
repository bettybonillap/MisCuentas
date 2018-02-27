package com.example.beatr.miscuentas;

import java.util.ArrayList;

/**
 * Created by beatr on 26/02/2018.
 */

public class Anio {
    public int anio;
    public ArrayList<Mes> meses;

    public Anio(int anio){
        this.anio=anio;
        meses=new ArrayList<Mes>();
    }
}
