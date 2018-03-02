package com.example.beatr.miscuentas;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private TextView date;
    private Button calendar;
    private EditText concepto,cantidad;
    private RadioButton ingreso,egreso;
    public ArrayList<Anio> anios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        date=(TextView)findViewById(R.id.fecha);
        calendar=(Button)findViewById(R.id.calendario);
        concepto=(EditText)findViewById(R.id.concepto);
        cantidad=(EditText)findViewById(R.id.cantidad);
        ingreso=(RadioButton)findViewById(R.id.ingreso);
        ingreso=(RadioButton)findViewById(R.id.ingreso);
        anios=new ArrayList<Anio>();
    }

    private final int REQUEST_CODE=7007;

    public void openCalendar(View view){
        Intent intent=new Intent(MainActivity.this,CalendarActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK && requestCode==REQUEST_CODE){
            if(data.hasExtra("date")){
                String fecha= data.getStringExtra("date");
                date.setText(fecha);
            }
        }
    }

    public void agrega(View view){
        String fecha=date.getText().toString();
        if(fecha!="") {
            String what = concepto.getText().toString();
            Log.d("Concepto", what);
            double cost = Double.parseDouble(cantidad.getText().toString());
            Log.d("Costo", Double.toString(cost));
            boolean tipo = false;
            if (ingreso.isChecked()) {
                Log.d("Costo", "ingreso");
                tipo = true;
            } else {
                Log.d("Costo", "egreso");
                tipo = false;
            }
            int anioA = Integer.parseInt(fecha.substring(fecha.lastIndexOf('/') + 1, fecha.length()));
            int mesA = Integer.parseInt(fecha.substring(fecha.indexOf('/') + 1, fecha.lastIndexOf('/')));
            int diaA = Integer.parseInt(fecha.substring(0, fecha.indexOf('/')));
            creaAnio(anioA);
            creaMes(anioA, mesA);
            creaSemana(anioA, mesA, diaA);
            creaDia(anioA, mesA, diaA);
            creaTransaccion(anioA, mesA, diaA, what, cost, tipo);
            //verT(anioA,mesA,diaA);
        }
    }

    public void creaAnio(int anioA){
        boolean norepetido=true;
        //la primera vez crear un año, después crear un año nuevo si no está ese año y añadirlo a la lista
        if(anios.size()==0){
            anios.add(new Anio(anioA));
            Log.d("Anio", Integer.toString(anios.size()));
        }else {
            for (Anio an : anios) {
                if (an.anio == anioA){
                    norepetido=false;
                }
            }
            if(norepetido==true){
                anios.add(new Anio(anioA));
            }
            Log.d("Anio", Integer.toString(anios.size()));
        }
    }

    public void creaMes(int anioA,int mesA){
        boolean norepetido=true;
        //para el año que tocó crear un nuevo mes si el el primer mes, después crear mes nuevo si no está ese mes y añadirlo
        for (Anio an : anios) {
            if (an.anio == anioA){
                Log.d("Crea mes:","año encontrado" );
                norepetido=true;
                if(an.meses.size()==0){
                    an.meses.add(new Mes(mesA));
                    Log.d("Mes", Integer.toString(an.meses.size()));
                }else {
                    for (Mes me : an.meses) {
                        if (me.mes == mesA){
                            norepetido=false;
                        }
                    }
                    if(norepetido==true){
                        an.meses.add(new Mes(mesA));
                    }
                    Log.d("Mes", Integer.toString(an.meses.size()));
                }
            }
        }
    }

    public void creaSemana(int anioA,int mesA, int diaA){
        boolean norepetido=true;
        int semanaA=0;
        switch (diaA){ //saber que semana es
            case 1: case 2: case 3: case 4: case 5: case 6: case 7:
                semanaA=1;
                Log.d("semana","1" );
                break;
            case 8: case 9: case 10: case 11: case 12: case 13: case 14:
                semanaA=2;
                Log.d("semana","2" );
                break;
            case 15: case 16: case 17: case 18: case 19: case 20: case 21:
                semanaA=3;
                Log.d("semana","3" );
                break;
            case 22: case 23: case 24: case 25: case 26: case 27: case 28:
                semanaA=4;
                Log.d("semana","4" );
                break;
            case 29: case 30: case 31:
                semanaA=5;
                Log.d("semana","5" );
                break;
        }

        for (Anio an : anios) {
            if (an.anio == anioA){
                Log.d("Crea semana","año encontrado" );
                for (Mes me : an.meses){
                    if (me.mes == mesA){
                        Log.d("Crea semana","mes encontrado" );
                        //crear la semana si no existe
                        norepetido=true;
                        if(me.semanas.size()==0){
                            me.semanas.add(new Semana(semanaA));
                            Log.d("Semana", Integer.toString(me.semanas.size()));
                        }else{
                            for (Semana sem : me.semanas){
                                if (sem.semana == semanaA){
                                    norepetido=false;
                                }
                            }
                            if(norepetido==true){
                                me.semanas.add(new Semana(semanaA));
                            }
                            Log.d("Semana", Integer.toString(me.semanas.size()));
                        }
                    }
                    //Log.d("Semanas todas", Integer.toString(me.semanas.size()));
                }

            }
        }

    }

    public void creaDia(int anioA,int mesA,int diaA){
        boolean norepetido=true;
        int semanaA=0;
        switch (diaA){ //saber que semana es
            case 1: case 2: case 3: case 4: case 5: case 6: case 7:
                semanaA=1;
                Log.d("semana","1" );
                break;
            case 8: case 9: case 10: case 11: case 12: case 13: case 14:
                semanaA=2;
                Log.d("semana","2" );
                break;
            case 15: case 16: case 17: case 18: case 19: case 20: case 21:
                semanaA=3;
                Log.d("semana","3" );
                break;
            case 22: case 23: case 24: case 25: case 26: case 27: case 28:
                semanaA=4;
                Log.d("semana","4" );
                break;
            case 29: case 30: case 31:
                semanaA=5;
                Log.d("semana","5" );
                break;
        }

        for (Anio an : anios) {
            if (an.anio == anioA){
                Log.d("Crea dia","año encontrado" );
                for (Mes me : an.meses){
                    if (me.mes == mesA){
                        Log.d("Crea dia","mes encontrado" );
                        for (Semana sem : me.semanas){
                            if (sem.semana == semanaA){
                                Log.d("Crea dia","semana encontrada" );
                                //crear nuevo dia si no existe
                                if(sem.dias.size()==0){
                                    sem.dias.add(new Dia(diaA));
                                    Log.d("tamano dias", Integer.toString(anios.size()));
                                }else {
                                    for (Dia di : sem.dias) {
                                        if (di.dia == diaA){
                                            norepetido=false;
                                        }
                                        if(norepetido==true){
                                            sem.dias.add(new Dia(diaA));
                                        }
                                        Log.d("Dia", Integer.toString(sem.dias.size()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void creaTransaccion(int anioA,int mesA,int diaA, String concepto,double cantidad,boolean tipo){
        int semanaA=0;
        switch (diaA){ //saber que semana es
            case 1: case 2: case 3: case 4: case 5: case 6: case 7:
                semanaA=1;
                Log.d("semana","1" );
                break;
            case 8: case 9: case 10: case 11: case 12: case 13: case 14:
                semanaA=2;
                Log.d("semana","2" );
                break;
            case 15: case 16: case 17: case 18: case 19: case 20: case 21:
                semanaA=3;
                Log.d("semana","3" );
                break;
            case 22: case 23: case 24: case 25: case 26: case 27: case 28:
                semanaA=4;
                Log.d("semana","4" );
                break;
            case 29: case 30: case 31:
                semanaA=5;
                Log.d("semana","5" );
                break;
        }

        for (Anio an : anios) {
            if (an.anio == anioA){
                Log.d("Crea transaccion","año encontrado" );
                for (Mes me : an.meses){
                    if (me.mes == mesA){
                        Log.d("Crea transaccion","mes encontrado" );
                        for (Semana sem : me.semanas){
                            if (sem.semana == semanaA){
                                Log.d("Crea transaccion","semana encontrada" );
                                for (Dia di : sem.dias) {
                                    if(di.dia==diaA){
                                        Log.d("Crea transaccion","dia encontrado");
                                        di.transacciones.add(new Transaccion(concepto,cantidad,tipo));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    public void verT(int anioA,int mesA,int diaA){
        int semanaA=0;
        switch (diaA){ //saber que semana es
            case 1: case 2: case 3: case 4: case 5: case 6: case 7:
                semanaA=1;
                Log.d("semana","1" );
                break;
            case 8: case 9: case 10: case 11: case 12: case 13: case 14:
                semanaA=2;
                Log.d("semana","2" );
                break;
            case 15: case 16: case 17: case 18: case 19: case 20: case 21:
                semanaA=3;
                Log.d("semana","3" );
                break;
            case 22: case 23: case 24: case 25: case 26: case 27: case 28:
                semanaA=4;
                Log.d("semana","4" );
                break;
            case 29: case 30: case 31:
                semanaA=5;
                Log.d("semana","5" );
                break;
        }

        for (Anio an : anios) {
            if (an.anio == anioA){
                Log.d("ver transaccion","año encontrado" );
                for (Mes me : an.meses){
                    if (me.mes == mesA){
                        Log.d("ver transaccion","mes encontrado" );
                        for (Semana sem : me.semanas){
                            if (sem.semana == semanaA){
                                Log.d("ver transaccion","semana encontrada" );
                                for (Dia di : sem.dias) {
                                    if(di.dia==diaA){
                                        Log.d("ver transaccion","dia encontrado");
                                        for(Transaccion tra:di.transacciones){
                                            Log.d("verT concepto",tra.concepto);
                                            Log.d("verT costo",Double.toString(tra.cantidad));
                                            Log.d("verT tipo",Boolean.toString(tra.tipo));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    public void elimina(View view){

    }

    public void ready(View view){
        Intent it =new Intent(MainActivity.this,ConsultaActivity.class);
        it.putParcelableArrayListExtra("aniolist", anios);
        startActivity(it);
    }
}
