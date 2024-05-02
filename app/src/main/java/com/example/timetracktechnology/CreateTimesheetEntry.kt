package com.example.timetracktechnology

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date

class CreateTimesheetEntry : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    lateinit var selectedTime: String

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0


    private lateinit var tvTitle: TextView
    private lateinit var edtTitle: EditText
    private lateinit var tvCategory: TextView
    private lateinit var spnCategory: Spinner
    private lateinit var tvStartTime: TextView
    private lateinit var tvEndTime: TextView
    private lateinit var btnDone: Button
    private lateinit var tvDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_timesheet_entry)

        val timeTrackApp = applicationContext as TimeTrackTechnology

        tvTitle = findViewById<TextView>(R.id.tvTitle)
        edtTitle = findViewById<EditText>(R.id.edtTitle)
        tvCategory = findViewById<TextView>(R.id.tvCategory)
        spnCategory = findViewById<Spinner>(R.id.spnCategory)
        tvEndTime = findViewById<TextView>(R.id.tvEndTime)
        btnDone = findViewById<Button>(R.id.btnDone)
        tvStartTime= findViewById<TextView>(R.id.tvStartTime)
        tvDate = findViewById<TextView>(R.id.tvDate)

        var startTime: Date? = null
        var endTime: Date? = null

        pickDateAndTime()

        val categoriesAdapter = ArrayAdapter<Category>(
            applicationContext,
            android.R.layout.simple_spinner_item,
            timeTrackApp.getCategoryList()
        )

        fun addNewTimesheetEntry(startTime: Date?, endTime: Date?) {
            val id = timeTrackApp.getTimesheetEntryId()
            val title = edtTitle.text.toString().trim()
            val category = spnCategory.selectedItemId.toInt()
            val entryDate = Date()

            val newTimesheetEntry =
                TimesheetEntry(id, title, category, entryDate, startTime!!, endTime!!)
            timeTrackApp.addToTimesheetEntryList(newTimesheetEntry)
        }

        btnDone.setOnClickListener() {
            if (startTime != null && endTime != null) {
                addNewTimesheetEntry(startTime, endTime)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Please select a start and end time",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun pickDateAndTime() {
        tvStartTime.setOnClickListener {
            getDateTimeCalendar()

            TimePickerDialog(this, startTimeListener, hour, minute, true).show()


        }

        tvEndTime.setOnClickListener {
            getDateTimeCalendar()

            TimePickerDialog(this, endTimeListener, hour, minute, true).show()
        }

        tvDate.setOnClickListener{
            getDateTimeCalendar()

            DatePickerDialog(this, dateClickListener, year, month, day ).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

    }

    private val startTimeListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        savedHour = hourOfDay
        savedMinute = minute
        getDateTimeCalendar()
        tvStartTime.text = String.format("%02d:%02d", savedHour, savedMinute)
    }

    private val endTimeListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        savedHour = hourOfDay
        savedMinute = minute
        getDateTimeCalendar()
        tvEndTime.text = String.format("%02d:%02d", savedHour, savedMinute)
    }

    private val dateClickListener = DatePickerDialog.OnDateSetListener { _, day , month, year ->
        savedDay = day
        savedMonth = month + 1
        savedYear = year
        getDateTimeCalendar()

        tvDate.text = String.format("%02d/%02d/%04d", savedDay, savedMonth, savedYear)
    }

    private fun getDateTimeCalendar(){
        val cal: Calendar = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR_OF_DAY)
        minute = cal.get(Calendar.MINUTE)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        TODO("Not yet implemented")
    }
}







