package com.example.timetracktechnology

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth

class BrowseTimesheetEntriesActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private  lateinit var btnAddNewEntry: Button
    private lateinit var rcvEntries: RecyclerView
    private lateinit var tvStartDate: TextView
    private lateinit var tvEndDate: TextView
    private lateinit var btnFilter: Button
    private lateinit var btnMenu: Button

    private lateinit var timesheetEntryList: ArrayList<TimesheetEntry>
    private lateinit var filteredEntryList: ArrayList<TimesheetEntry>

    private lateinit var startDate: LocalDate
    private lateinit var endDate: LocalDate

    private var day = 0
    private var month = 0
    private var year = 0

    private var savedDay = 0
    private var savedMonth = 0
    private var savedYear = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_timesheet_entries)

        val myApp = application as TimeTrackTechnology

        rcvEntries = findViewById<RecyclerView>(R.id.rcvTimesheetEntries)
        btnAddNewEntry = findViewById<Button>(R.id.btnAddNewEntry)
        btnFilter = findViewById<Button>(R.id.btnFilter)
        btnMenu = findViewById<Button>(R.id.btnMenu)
        tvStartDate = findViewById<TextView>(R.id.tvStartDate)
        tvEndDate = findViewById<TextView>(R.id.tvEndDate)

        timesheetEntryList = myApp.getTimesheetEntryList()

        filteredEntryList = ArrayList<TimesheetEntry>()

        btnAddNewEntry.setOnClickListener {
            val intent = Intent(this, CreateTimesheetEntry::class.java)
            startActivity(intent)
        }

        populateRecyclerView(timesheetEntryList)

        pickDate()

        btnFilter.setOnClickListener{
            try {
                filterEntriesByDate(startDate, endDate)
                populateRecyclerView(filteredEntryList)
            }
            catch (exc: Exception){
                Toast.makeText(applicationContext, "An error occurred: ${exc.message}", Toast.LENGTH_SHORT).show()
            }
        }

        btnMenu.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    private fun populateRecyclerView(entryList: ArrayList<TimesheetEntry>){
        try {
            val entriesAdapter = TImesheetEntriesRecyclerViewAdapter(this, entryList)
            rcvEntries.adapter = entriesAdapter
            val layoutManager = LinearLayoutManager(this)
            rcvEntries.layoutManager = layoutManager
        }
        catch (exc: Exception) {
            exc.printStackTrace()
        }
    }

    private fun filterEntriesByDate(startDate: LocalDate, endDate: LocalDate){
        for (entry  in timesheetEntryList){
            if(entry.entryDate > startDate && entry.entryDate < endDate){
                filteredEntryList.add(entry)
            }
        }
    }

    private fun pickDate(){
        tvStartDate.setOnClickListener{
            getDateTimeCalendar()

            DatePickerDialog(this, startDateClickListener, year, month, day ).show()
        }

        tvEndDate.setOnClickListener{
            getDateTimeCalendar()

            DatePickerDialog(this, endDateClickListener, year, month, day ).show()
        }
    }

    private fun getDateTimeCalendar(){
        val currenDateTime = LocalDateTime.now()
        day = currenDateTime.dayOfMonth
        month = currenDateTime.monthValue
        year = currenDateTime.year
    }


    //Listener for selecting start date
    private val startDateClickListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        savedDay = day
        savedMonth = month + 1
        savedYear = year
        getDateTimeCalendar()

        val yearMonth = YearMonth.of(savedYear, savedMonth)
        if (yearMonth.isValidDay(savedDay)) {

            getDateTimeCalendar()
            startDate = LocalDate.of(savedYear, savedMonth, savedDay)
            tvStartDate.text = String.format("%02d/%02d/%04d", savedDay, savedMonth - 1, savedYear)

        } else {

            Toast.makeText(
                this,
                "Please select a valid date",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    //Listener for selecting end date
    private val endDateClickListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
        savedDay = day
        savedMonth = month + 1
        savedYear = year
        getDateTimeCalendar()

        val yearMonth = YearMonth.of(savedYear, savedMonth)
        if (yearMonth.isValidDay(savedDay)) {

            getDateTimeCalendar()
            endDate = LocalDate.of(savedYear, savedMonth, savedDay)
            tvStartDate.text = String.format("%02d/%02d/%04d", savedDay, savedMonth - 1, savedYear)

        } else {

            Toast.makeText(
                this,
                "Please select a valid date",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        TODO("Not yet implemented")
    }
}