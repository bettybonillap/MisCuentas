package com.example.beatr.miscuentas;

import java.util.ArrayList;

/**
 * Created by beatr on 26/02/2018.
 */

public class Semana {
    public int semana;
    public ArrayList<Dia> dias;
    public Semana(){
    }
    public Semana(int semana){
        this.semana=semana;
        dias=new ArrayList<Dia>();
    }
}
