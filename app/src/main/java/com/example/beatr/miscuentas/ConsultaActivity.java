package com.example.beatr.miscuentas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ConsultaActivity extends Activity {
    private TextView date;
    public ArrayList<Anio> anios;
    private Button calendar;
    private CuentasAdapter cuentasAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        anios=new ArrayList<Anio>();
        anios= getIntent().getParcelableArrayListExtra("aniolist");
        date=(TextView)findViewById(R.id.fecha);
        calendar=(Button)findViewById(R.id.calendario);
        listView=(ListView) findViewById(R.id.listView);
        cuentasAdapter= new CuentasAdapter(this, R.layout.cuentas_transacciones_layout, new ArrayList<Transaccion>());
        listView.setAdapter(cuentasAdapter);

        if(anios.size()!=0){


        }else{
            //return;
        }
    }

    private final int REQUEST_CODE=7007;

    public void openCalendar(View view){
        Intent intent=new Intent(ConsultaActivity.this,CalendarActivity.class);
        startActivityForResult(intent,REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK && requestCode==REQUEST_CODE){
            if(data.hasExtra("date")){
                //Persona p1=(Persona)data.getSerializableExtra("persona");
                String fecha= data.getStringExtra("date");
                date.setText(fecha);
                int anioA=Integer.parseInt(fecha.substring(fecha.lastIndexOf('/')+1,fecha.length()));
                int mesA=Integer.parseInt(fecha.substring(fecha.indexOf('/')+1,fecha.lastIndexOf('/')));
                int diaA=Integer.parseInt(fecha.substring(0,fecha.indexOf('/')));
                cuentasAdapter.clear();
                ArrayList<Transaccion> transacciones=findTransacciones(anioA,mesA,diaA);
                if(transacciones.size()!=0){
                    for(Transaccion tra:transacciones) {
                        cuentasAdapter.add(tra);
                    }
                }

            }
        }
    }

    public ArrayList<Transaccion> findTransacciones(int anioA,int mesA,int diaA){
        int semanaA=0;
        switch (diaA){ //saber que semana es
            case 1: case 2: case 3: case 4: case 5: case 6: case 7:
                semanaA=1;
                break;
            case 8: case 9: case 10: case 11: case 12: case 13: case 14:
                semanaA=2;
                break;
            case 15: case 16: case 17: case 18: case 19: case 20: case 21:
                semanaA=3;
                break;
            case 22: case 23: case 24: case 25: case 26: case 27: case 28:
                semanaA=4;
                break;
            case 29: case 30: case 31:
                semanaA=5;
                break;
        }

        ArrayList<Transaccion> transacciones=new ArrayList<Transaccion>();
        for (Anio an : anios) {
            if (an.anio == anioA) {
                for (Mes me : an.meses) {
                    if (me.mes == mesA) {
                        for (Semana sem : me.semanas) {
                            if (sem.semana == semanaA) {
                                for (Dia di : sem.dias) {
                                    if (di.dia == diaA) {
                                        transacciones=di.transacciones;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return transacciones;
    }
}
