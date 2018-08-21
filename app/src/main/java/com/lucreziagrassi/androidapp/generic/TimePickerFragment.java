package com.lucreziagrassi.androidapp.generic;

import android.os.Bundle;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.app.TimePickerDialog;
import android.widget.EditText;
import android.widget.TimePicker;

import com.lucreziagrassi.androidapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Locale.setDefault(Locale.ITALY);
        // Use the current time as the default values for the picker
        Calendar c;
        c = Calendar.getInstance(Locale.ITALY);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), R.style.DatePicker, this, hour, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance(Locale.ITALY);
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ITALY);
        String formattedTime = sdf.format(c.getTime());
        EditText editText = (EditText) getActivity().findViewById(this.getArguments().getInt("timeEditTextID"));
        editText.setText(formattedTime);
    }
}