package com.example.beatr.miscuentas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() { //para cambiar a la siguiente actividad despues de un tiempo
            @Override
            public void run() {
                Intent it =new Intent(SplashActivity.this,MainActivity.class);
                startActivity(it);
                finish();
            }
        },4000 );
    }
}
