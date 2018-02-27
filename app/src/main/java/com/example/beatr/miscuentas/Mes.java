package com.example.beatr.miscuentas;

import java.util.ArrayList;

/**
 * Created by beatr on 26/02/2018.
 */

public class Mes {
    public int mes;
    public ArrayList<Semana> semanas;
    public Mes(){
    }
    public Mes(int mes){
        this.mes=mes;
        semanas=new ArrayList<Semana>();
    }
}
