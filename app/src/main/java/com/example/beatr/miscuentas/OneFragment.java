package com.example.beatr.miscuentas;


import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.ContentValues.TAG;


public class OneFragment extends Fragment {
    static final int DIALOG_ID=0;

    ArrayList<String> conceptos = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    private Button calendar, agregar, eliminar;
    AutoCompleteTextView concepto, cantidad;
    TextView date;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    public ArrayList<Anio> anios;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conceptos.add(getResources().getString(R.string.concepto_ex));
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("transacciones");
        anios=new ArrayList<Anio>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //agregar conceptos de firebase
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line, conceptos);
        concepto = (AutoCompleteTextView)getView().findViewById(R.id.concepto);
        cantidad = (AutoCompleteTextView)getView().findViewById(R.id.cantidad);
        concepto.setAdapter(adapter);

        date=(TextView)getView().findViewById(R.id.fecha);
        calendar=(Button)getView().findViewById(R.id.calendario);
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year= calendar.get(Calendar.YEAR);
                int month=calendar.get(Calendar.MONTH);
                int day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        String fecha = String.valueOf(dayOfMonth) +"/"+String.valueOf(monthOfYear+1)
                                +"/"+String.valueOf(year);
                        date.setText(fecha);
                    }
                }, year, month, day);

                datePicker.show();
            }
        });

        agregar=(Button)getView().findViewById(R.id.agregar);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fecha=date.getText().toString();
                if(!fecha.equalsIgnoreCase("")) {
                    String what = concepto.getText().toString();
                    if(TextUtils.isEmpty(what)) {//validacion
                        concepto.setError(getResources().getString(R.string.error));
                        return;
                    }
                    if(TextUtils.isEmpty(cantidad.getText().toString())) {//validacion
                        cantidad.setError(getResources().getString(R.string.error));
                        return;
                    }
                    double cost = Double.parseDouble(cantidad.getText().toString());
                   // Log.d("Concepto", what);
                   // Log.d("Costo", Double.toString(cost));
                    boolean tipo = true;

                    Transaccion transaccion= new Transaccion(what,cost,tipo,fecha);
                    databaseReference.push().setValue(transaccion);
                    //verT(anioA,mesA,diaA);
                    Toast.makeText(getContext(),getResources().getString(R.string.add), Toast.LENGTH_SHORT).show();
                }
            }
        });

        eliminar=(Button)getView().findViewById(R.id.eliminar);
        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String fecha=date.getText().toString();
                Query query = databaseReference.orderByChild("fecha").equalTo(date.getText().toString());

                final String what = concepto.getText().toString();
                if(TextUtils.isEmpty(what)) {//validacion
                    concepto.setError(getResources().getString(R.string.error));
                    return;
                }
                if(TextUtils.isEmpty(cantidad.getText().toString())) {//validacion
                    cantidad.setError(getResources().getString(R.string.error));
                    return;
                }
                final double cost = Double.parseDouble(cantidad.getText().toString());

                final boolean tipo = true;

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getChildrenCount() > 0){
                            Boolean justOne=false;
                            for(DataSnapshot itemSnapshot : dataSnapshot.getChildren()){
                                Transaccion transaccion= itemSnapshot.getValue(Transaccion.class);
                                //System.out.println(itemSnapshot.getKey());
                                //System.out.println(transaccion.concepto);
                                //System.out.println(transaccion.cantidad);
                                //System.out.println(transaccion.tipo);
                                if(fecha!="" && justOne==false && transaccion.cantidad==cost && transaccion.tipo==tipo &&
                                        what.equalsIgnoreCase(transaccion.concepto)){
                                    itemSnapshot.getRef().removeValue();
                                    Toast.makeText(getContext(),getResources().getString(R.string.delete), Toast.LENGTH_SHORT).show();
                                    justOne=true;
                                }
                            }
                        }else{
                            Toast.makeText(getContext(),getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }

    public void creaAnio(int anioA){
        boolean norepetido=true;
        //la primera vez crear un año, después crear un año nuevo si no está ese año y añadirlo a la lista
        if(anios.size()==0){
            anios.add(new Anio(anioA));
           // Log.d("Anio", Integer.toString(anios.size()));
        }else {
            for (Anio an : anios) {
                if (an.anio == anioA){
                    norepetido=false;
                }
            }
            if(norepetido==true){
                anios.add(new Anio(anioA));
            }
            //Log.d("Anio", Integer.toString(anios.size()));
        }
    }

    public void creaMes(int anioA,int mesA){
        boolean norepetido=true;
        //para el año que tocó crear un nuevo mes si el el primer mes, después crear mes nuevo si no está ese mes y añadirlo
        for (Anio an : anios) {
            if (an.anio == anioA){
               // Log.d("Crea mes:","año encontrado" );
                norepetido=true;
                if(an.meses.size()==0){
                    an.meses.add(new Mes(mesA));
                   // Log.d("Mes", Integer.toString(an.meses.size()));
                }else {
                    for (Mes me : an.meses) {
                        if (me.mes == mesA){
                            norepetido=false;
                        }
                    }
                    if(norepetido==true){
                        an.meses.add(new Mes(mesA));
                    }
                  //  Log.d("Mes", Integer.toString(an.meses.size()));
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
               // Log.d("semana","1" );
                break;
            case 8: case 9: case 10: case 11: case 12: case 13: case 14:
                semanaA=2;
               // Log.d("semana","2" );
                break;
            case 15: case 16: case 17: case 18: case 19: case 20: case 21:
                semanaA=3;
               // Log.d("semana","3" );
                break;
            case 22: case 23: case 24: case 25: case 26: case 27: case 28:
                semanaA=4;
              //  Log.d("semana","4" );
                break;
            case 29: case 30: case 31:
                semanaA=5;
               // Log.d("semana","5" );
                break;
        }

        for (Anio an : anios) {
            if (an.anio == anioA){
                //Log.d("Crea semana","año encontrado" );
                for (Mes me : an.meses){
                    if (me.mes == mesA){
                  //      Log.d("Crea semana","mes encontrado" );
                        //crear la semana si no existe
                        norepetido=true;
                        if(me.semanas.size()==0){
                            me.semanas.add(new Semana(semanaA));
                    //        Log.d("Semana", Integer.toString(me.semanas.size()));
                        }else{
                            for (Semana sem : me.semanas){
                                if (sem.semana == semanaA){
                                    norepetido=false;
                                }
                            }
                            if(norepetido==true){
                                me.semanas.add(new Semana(semanaA));
                            }
                      //      Log.d("Semana", Integer.toString(me.semanas.size()));
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
               // Log.d("semana","1" );
                break;
            case 8: case 9: case 10: case 11: case 12: case 13: case 14:
                semanaA=2;
                //Log.d("semana","2" );
                break;
            case 15: case 16: case 17: case 18: case 19: case 20: case 21:
                semanaA=3;
                //Log.d("semana","3" );
                break;
            case 22: case 23: case 24: case 25: case 26: case 27: case 28:
                semanaA=4;
                //Log.d("semana","4" );
                break;
            case 29: case 30: case 31:
                semanaA=5;
                //Log.d("semana","5" );
                break;
        }

        for (Anio an : anios) {
            if (an.anio == anioA){
               // Log.d("Crea dia","año encontrado" );
                for (Mes me : an.meses){
                    if (me.mes == mesA){
                 //       Log.d("Crea dia","mes encontrado" );
                        for (Semana sem : me.semanas){
                            if (sem.semana == semanaA){
                   //             Log.d("Crea dia","semana encontrada" );
                                //crear nuevo dia si no existe
                                if(sem.dias.size()==0){
                                    sem.dias.add(new Dia(diaA));
                     //               Log.d("tamano dias", Integer.toString(anios.size()));
                                }else {
                                    for (Dia di : sem.dias) {
                                        if (di.dia == diaA){
                                            norepetido=false;
                                        }
                                        if(norepetido==true){
                                            sem.dias.add(new Dia(diaA));
                                        }
                       //                 Log.d("Dia", Integer.toString(sem.dias.size()));
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
               // Log.d("semana","1" );
                break;
            case 8: case 9: case 10: case 11: case 12: case 13: case 14:
                semanaA=2;
               // Log.d("semana","2" );
                break;
            case 15: case 16: case 17: case 18: case 19: case 20: case 21:
                semanaA=3;
                //Log.d("semana","3" );
                break;
            case 22: case 23: case 24: case 25: case 26: case 27: case 28:
                semanaA=4;
                //Log.d("semana","4" );
                break;
            case 29: case 30: case 31:
                semanaA=5;
                //Log.d("semana","5" );
                break;
        }

        for (Anio an : anios) {
            if (an.anio == anioA){
               // Log.d("Crea transaccion","año encontrado" );
                for (Mes me : an.meses){
                    if (me.mes == mesA){
                        //Log.d("Crea transaccion","mes encontrado" );
                        for (Semana sem : me.semanas){
                            if (sem.semana == semanaA){
                                //Log.d("Crea transaccion","semana encontrada" );
                                for (Dia di : sem.dias) {
                                    if(di.dia==diaA){
                                       // Log.d("Crea transaccion","dia encontrado");
                                        //Transaccion transaccion= new Transaccion(concepto,cantidad,tipo);
                                        //di.transacciones.add(transaccion);
                                        //databaseReference.push().setValue(transaccion);
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
