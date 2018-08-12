package com.lucreziagrassi.androidapp;
import android.os.Bundle;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Locale locale = getResources().getConfiguration().locale;
        Locale.setDefault(locale);
        // Use the current time as the default values for the picker
        Calendar c;
        c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(hourOfDay, minute);

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm", Locale.ITALY);
        String formattedTime = sdf.format(c.getTime());
        EditText start_hour = (EditText) getActivity().findViewById(R.id.start_hour);
        start_hour.setHint(formattedTime);
    }
}