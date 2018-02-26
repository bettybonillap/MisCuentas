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

public class MainActivity extends Activity {
    private TextView date;
    private Button calendar;
    private EditText concepto,cantidad;
    private RadioButton ingreso,egreso;

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
                //Persona p1=(Persona)data.getSerializableExtra("persona");
                String fecha= data.getStringExtra("date");
                date.setText(fecha);
            }
        }
    }
    public void agrega(View view){
        String what=concepto.getText().toString();
        Log.d("Concepto", what);
        double cost=Double.parseDouble(cantidad.getText().toString());
        Log.d("Costo", Double.toString(cost));
        
        if(ingreso.isChecked()){
            Log.d("Costo", "ingreso");
        }else{
            Log.d("Costo", "egreso");
        }
    }
    public void elimina(View view){

    }
}
