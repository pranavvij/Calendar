#Calendar
This repo contains a dynamic calendar which created month based on the month choosed from date picker,the rows in this are created dynaimically based on the number of days in a month.
[alt text](https://github.com/pranavvij/Calendar/blob/master/images/12986516_708259032647306_1113348939_o.png "CalendarView")

```
<com.noc.customcalendar.CalendarView
        android:id="@+id/calendar"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
        
 calendarView=(CalendarView)findViewById(R.id.calendar);
        calendarView.daysOfMonth(2016, 4);
        View.OnClickListener listener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.newInstance(MainActivity.this, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).show(getFragmentManager(), "datePicker");
            }
        };
        calendarView.setCalendarListener(listener);
```
