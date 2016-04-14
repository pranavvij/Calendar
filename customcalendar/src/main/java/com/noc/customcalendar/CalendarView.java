package com.noc.customcalendar;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Sunny on 08/03/16 for calendar.
 */
public class CalendarView extends LinearLayout implements View.OnClickListener {
    View v;
    TextView[][] textArray;
    LayoutInflater inflater;
    Context context;
    View[] child;
    LinearLayout container;
    TextView monthTxt,yearTxt;
    DateTime dateTime;
    int monthValue,yearValue;
    TextView[] hightLighted=new TextView[7];
    Boolean isHighlighted=false;
    int startDateGap;
    int rows;
    int datePlusDateShift;
    int numberOfDaysInCurrentMonth;
    public static String months[] = {"Jan", "Feb", "Mar", "April",
            "May", "Jun", "July", "Aug", "Sept",
            "Oct", "Nov", "Dec"};
    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflateView(context);
    }

    public CalendarView(Context context) {
        super(context);
        inflateView(context);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        inflateView(context);
    }
    private void inflateView(Context context){
        this.context=context;
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v=inflater.inflate(R.layout.calendar_layout, this, true);
        container=(LinearLayout)v.findViewById(R.id.container_months);
        monthTxt=(TextView)v.findViewById(R.id.monthTxt);
        yearTxt=(TextView)v.findViewById(R.id.yearTxt);
    }
    public void setCalendarListener(OnClickListener listener){
        monthTxt.setOnClickListener(listener);
    }
    public void daysOfMonth(int year, int month) {
        monthValue=month;
        yearValue=year;
        monthTxt.setText("" + months[month - 1]);
        yearTxt.setText("" + year);
        dateTime = new DateTime(year, month, 1, 12, 0, 0, 000);
        startDateGap=dateTime.getDayOfWeek()%7;
        datePlusDateShift=dateTime.dayOfMonth().getMaximumValue()+startDateGap;
        numberOfDaysInCurrentMonth=dateTime.dayOfMonth().getMaximumValue();
        int shift=0;
        if(datePlusDateShift%7!=0){
            shift=shift+1;
        }
        rows=(datePlusDateShift / 7) + shift;
        Log.d("tagged", datePlusDateShift + " " + rows + " " + shift + " " + dateTime.getDayOfWeek() + " " + dateTime.dayOfMonth().getMaximumValue());
        inflateWeekViews(dateTime.getDayOfWeek());
    }
    private void inflateWeekViews(int start){
        int numberOfDaysinPreviousMonth=dateTime.minusMonths(1).dayOfMonth().getMaximumValue();
        if(inflater==null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        child=new View[rows];
        textArray=new TextView[rows][7];

        for(int row=0;row<rows;row++) {
            child[row]=inflater.inflate(R.layout.weekcell, null, true);

            textArray[row][0]=(TextView)child[row].findViewById(R.id.first_txt);
            textArray[row][1]=(TextView)child[row].findViewById(R.id.second_txt);
            textArray[row][2]=(TextView)child[row].findViewById(R.id.third_txt);
            textArray[row][3]=(TextView)child[row].findViewById(R.id.fourth_txt);
            textArray[row][4]=(TextView)child[row].findViewById(R.id.fifth_txt);
            textArray[row][5]=(TextView)child[row].findViewById(R.id.sixth_txt);
            textArray[row][6]=(TextView)child[row].findViewById(R.id.seventh_txt);
            container.addView(child[row]);
        }

        for(int startDate=start-1;startDate>=0;startDate--){
            textArray[0][startDate].setText(""+numberOfDaysinPreviousMonth);
            numberOfDaysinPreviousMonth--;
            textArray[0][startDate].setTextColor(getResources().getColor(R.color.grey));
        }
        int dateInc=1;
        int row=0;

        while(dateInc<=numberOfDaysInCurrentMonth){
            textArray[row][start].setText(""+dateInc);
            textArray[row][start].setOnClickListener(this);
            dateInc++;
            start++;
            if(start%7==0){
                row++;
                start=0;
            }
            Log.d("rows",""+row);
        }
        Log.d("start", "" + start);
        while (start%7!=0){
            textArray[row][start].setText("");
            start++;
            dateInc++;
        }
    }
    private void hightlighted(int date){
        DateTime time=dateTime.plusDays(date-1);
        Log.d("viewName",""+time);
        time=time.minusDays(time.getDayOfWeek());
        Log.d("viewName",""+time);
        for(int i=0;i<7;i++){
            if(i==0) {
                hightLighted[i] = convertDateToTextView(time.getDayOfMonth() + i);
                hightLighted[i].setBackgroundDrawable(context.getResources().getDrawable(R.drawable.left_rounded));
            }
            else if(i==6){
                hightLighted[i] = convertDateToTextView(time.getDayOfMonth() + i);
                hightLighted[i].setBackgroundDrawable(context.getResources().getDrawable(R.drawable.right_rounded));
            }
            else{
                hightLighted[i] = convertDateToTextView(time.getDayOfMonth() + i);
                hightLighted[i].setBackgroundColor(context.getResources().getColor(R.color.transparent_black));
            }
        }

    }
    private TextView convertDateToTextView(int date){
        int shiftDate=dateTime.getDayOfWeek()+date-1;
        return textArray[shiftDate/7][(shiftDate%7)];
    }

    @Override
    public void onClick(View v) {
        TextView view=(TextView)v;
        if(isHighlighted){
            removeHighlighting();
            hightlighted(Integer.parseInt(view.getText().toString()));
        }
        else{
            hightlighted(Integer.parseInt(view.getText().toString()));
            isHighlighted=true;
        }
    }

    private void removeHighlighting() {
        for(int i=0;i<7;i++){
            if(hightLighted[i]!=null){
                hightLighted[i].setBackgroundColor(context.getResources().getColor(R.color.transparent));
            }
        }
    }
    public void updateDateTime(int year,int date){
        container.removeAllViews();
        daysOfMonth(year,date);
    }
}
