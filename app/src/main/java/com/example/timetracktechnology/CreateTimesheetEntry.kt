package com.example.timetracktechnology

import android.icu.text.CaseMap.Title
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.Date

class CreateTimesheetEntry : AppCompatActivity() {

    public lateinit var selectedTime: Date;
    val timeTrackApp = applicationContext as TimeTrackTechnology

    private lateinit var tvTitle: TextView
    private lateinit var edtTitle: EditText
    private lateinit var tvCategory: TextView
    private lateinit var spnCategory: Spinner
    private lateinit var tvStartTime: TextView
    private lateinit var tvEndTime: TextView
    private lateinit var btnDone: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_timesheet_entry)

        tvTitle = findViewById<TextView>(R.id.tvTitle)
        edtTitle = findViewById<EditText>(R.id.edtTitle)
        tvCategory = findViewById<TextView>(R.id.tvCategory)
        spnCategory = findViewById<Spinner>(R.id.spnCategory)
        tvStartTime = findViewById<TextView>(R.id.tvStartTime)
        tvEndTime = findViewById<TextView>(R.id.tvEndTime)
        btnDone = findViewById<Button>(R.id.btnDone)

        var startTime: Date
        var endTime: Date

        tvStartTime.setOnClickListener {
            TimePickerFragment("startTime").show(supportFragmentManager, "timePicker")
            startTime = selectedTime
        }

        tvEndTime.setOnClickListener {
            TimePickerFragment("endTime").show(supportFragmentManager, "timePicker")
            endTime = selectedTime
        }

        tvEndTime.setOnClickListener {
            DatePickerFragment().show(supportFragmentManager, "datePicker")
            endTime = selectedTime
        }
    }

    private fun addNewTimesheetEntry(startTime: Time, endTime: Time){
        val id = timeTrackApp.getTimesheetEntryId()
        val title = edtTitle.text.toString().trim()
        val category = spnCategory.selectedItemId.toInt()
        val entryDate = Date()

        val newTimesheetEntry = TimesheetEntry(id, title, category, entryDate, startTime, endTime)
        timeTrackApp.addToTimesheetEntryList(newTimesheetEntry)
    }

    private fun getStartTime(){

    }

}