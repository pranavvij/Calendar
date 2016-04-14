package com.noc.calendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.datetimepicker.date.DatePickerDialog;
import com.noc.customcalendar.CalendarView;

import org.joda.time.LocalDateTime;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView=(CalendarView)findViewById(R.id.calendar);
        calendarView.daysOfMonth(2016, 4);
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.newInstance(MainActivity.this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
            }
        };
        calendarView.setCalendarListener(listener);
    }

    @Override
    public void onDateSet(DatePickerDialog dialog, int year, int monthOfYear, int dayOfMonth) {
        calendarView.updateDateTime(year, monthOfYear+1);
    }
}
