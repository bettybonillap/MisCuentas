package com.example.beatr.miscuentas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

import static android.content.ContentValues.TAG;

/**
 * Created by beatr on 25/02/2018.
 */

public class CalendarActivity extends Activity {
    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);
        calendarView=  (CalendarView)findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int i, int i1, int i2) {
                String date=i2+"/"+(i1+1)+"/"+i;
                //Log.d(TAG,"onSelectedDayChange: date: "+date);
                close(date);
            }
        });
    }

    public void close(String date){
        Intent intent=new Intent();
        intent.putExtra("date",date);
        setResult(RESULT_OK,intent);
        super.finish();
    }

}
