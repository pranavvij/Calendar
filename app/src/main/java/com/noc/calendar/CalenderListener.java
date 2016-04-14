package com.noc.calendar;

import org.joda.time.LocalDateTime;

public abstract class CalenderListener {

    public abstract void onSelectDate(LocalDateTime mSelectedDate);


    public abstract void onSelectPicker();

}
