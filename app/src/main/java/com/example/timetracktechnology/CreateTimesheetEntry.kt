package com.example.timetracktechnology

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
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
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.LocalDate
import java.time.YearMonth

class CreateTimesheetEntry : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var startTime: LocalTime
    private lateinit var endTime: LocalTime
    private lateinit var entryDate: LocalDate

    private var categoryId: Int = 0

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var savedDay = 0
    private var savedMonth = 0
    private var savedYear = 0
    private var savedHour = 0
    private var savedMinute = 0


    private lateinit var tvTitle: TextView
    private lateinit var edtTitle: EditText
    private lateinit var tvCategory: TextView
    private lateinit var spnCategory: Spinner
    private lateinit var tvStartTime: TextView
    private lateinit var tvEndTime: TextView
    private lateinit var btnDone: Button
    private lateinit var tvDate: TextView
    private  lateinit var btnBrowseEntries: Button

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
        btnBrowseEntries = findViewById<Button>(R.id.btnBrowseEntries)

        pickDateAndTime()

        val categoriesAdapter = CategoriesSpinnerAdapter(
            applicationContext,
            timeTrackApp.getCategoryList()
        )

       // categoriesAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice)
        spnCategory.adapter = categoriesAdapter

        fun addNewTimesheetEntry(startTime: LocalTime, endTime: LocalTime, entryDate: LocalDate) {
            val id = timeTrackApp.getTimesheetEntryId()
            val title = edtTitle.text.toString().trim()
            val category = spnCategory.selectedItemId.toInt()

            val newTimesheetEntry =
                TimesheetEntry(id, title, category, entryDate, startTime, endTime)
            timeTrackApp.addToTimesheetEntryList(newTimesheetEntry)
        }

        btnDone.setOnClickListener() {

            addNewTimesheetEntry(startTime, endTime, entryDate)

            Toast.makeText(
                applicationContext,
                "New Timesheet Entry Added!",
                Toast.LENGTH_SHORT
            ).show()

        }

        btnBrowseEntries.setOnClickListener(){
            val intent = Intent(this, BrowseTimesheetEntriesActivity::class.java)
            startActivity(intent)
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

        startTime = LocalTime.of(savedHour, savedMinute)
        tvStartTime.text = String.format("%02d:%02d", savedHour, savedMinute)
    }

    private val endTimeListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        savedHour = hourOfDay
        savedMinute = minute
        getDateTimeCalendar()

        endTime = LocalTime.of(savedHour, savedMinute)
        tvEndTime.text = String.format("%02d:%02d", savedHour, savedMinute)
    }

    private val dateClickListener = DatePickerDialog.OnDateSetListener { _, year , month, day ->
        savedDay = day
        savedMonth = month + 1
        savedYear = year
        getDateTimeCalendar()

        val yearMonth = YearMonth.of(savedYear, savedMonth)
        if (yearMonth.isValidDay(savedDay)) {

            getDateTimeCalendar()
            entryDate = LocalDate.of(savedYear, savedMonth, savedDay)
            tvDate.text = String.format("%02d/%02d/%04d", savedDay, savedMonth - 1, savedYear)

        } else {

            Toast.makeText(this,
                "Please select a valid date",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    private fun getDateTimeCalendar(){
        val currenDateTime = LocalDateTime.now()
        day = currenDateTime.dayOfMonth
        month = currenDateTime.monthValue
        year = currenDateTime.year
        hour = currenDateTime.hour
        minute = currenDateTime.minute
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        TODO("Not yet implemented")
    }
}







