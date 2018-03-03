package com.example.beatr.miscuentas;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by beatr on 01/03/2018.
 */

public class CuentasAdapter extends ArrayAdapter<Transaccion> {
    private Context context;

    public CuentasAdapter(@NonNull Context context, int resource, @NonNull List<Transaccion> objects) {
        super(context, resource, objects);
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.cuentas_transacciones_layout,parent,false);
        }
        Transaccion transaccion=getItem(position);
        TextView con =(TextView) convertView.findViewById(R.id.concepto);
        TextView can =(TextView) convertView.findViewById(R.id.cantidad);
        con.setText(transaccion.concepto);
        String t="";
        if(!transaccion.tipo){
            t="-";
        }
        can.setText(t+Double.toString(transaccion.cantidad));
        return convertView;
    }
}
