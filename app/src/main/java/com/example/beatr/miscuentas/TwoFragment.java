package com.example.beatr.miscuentas;



import android.app.DatePickerDialog;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class TwoFragment extends Fragment {
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

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        conceptos.add(getResources().getString(R.string.concepto_ex2));
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("transacciones");
        anios=new ArrayList<Anio>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_two, container, false);
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
                    //Log.d("Concepto", what);
                    //Log.d("Costo", Double.toString(cost));
                    boolean tipo = false;

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

                final boolean tipo = false;

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
                            Toast.makeText(getContext(),"Failed to delete", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
