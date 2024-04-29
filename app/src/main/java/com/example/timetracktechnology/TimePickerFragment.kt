package com.example.timetracktechnology

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class TimePickerFragment(val timePickerType: String) : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //use the current time as the default values for the picker.
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        //return an instance of the timepicker dialog
        return TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
    }

    //returns selected time
    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        //return a calendar object that uses only time
        val CreateTimesheetEntry = activity as CreateTimesheetEntry
        CreateTimesheetEntry.selectedTime = calendar.time;
    }
}