package com.example.timetracktechnology

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.YearMonth

class BrowseTimesheetEntriesActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    val db = Firebase.firestore

    private  lateinit var btnAddNewEntry: Button
    private lateinit var rcvEntries: RecyclerView
    private lateinit var tvStartDate: TextView
    private lateinit var tvEndDate: TextView
    private lateinit var btnFilter: Button
    private lateinit var btnMenu: Button
    private lateinit var tvMinDailyGoal: TextView
    private lateinit var tvMaxDailyGoal: TextView

    private lateinit var timesheetEntryList: ArrayList<TimesheetEntry>
    private lateinit var filteredEntryList: ArrayList<TimesheetEntry>

    private lateinit var minGoal: MinGoal
    private lateinit var maxGoal: MaxGoal

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

        var myApp = application as TimeTrackTechnology

        rcvEntries = findViewById<RecyclerView>(R.id.rcvTimesheetEntries)
        btnAddNewEntry = findViewById<Button>(R.id.btnAddNewEntry)
        btnFilter = findViewById<Button>(R.id.btnFilter)
        btnMenu = findViewById<Button>(R.id.btnMenu)
        tvStartDate = findViewById<TextView>(R.id.tvStartDate)
        tvEndDate = findViewById<TextView>(R.id.tvEndDate)
        tvMaxDailyGoal = findViewById<TextView>(R.id.tvMaxGoal)
        tvMinDailyGoal = findViewById<TextView>(R.id.tvMinGoal)

        timesheetEntryList = ArrayList<TimesheetEntry>()
        getEntriesFromFirestore()

        filteredEntryList = ArrayList<TimesheetEntry>()

        myApp = application as TimeTrackTechnology

        val minGoal: Int? = myApp.minDailyGoal
        val maxGoal: Int? = myApp.maxDailyGoal

        if (minGoal != null){
            tvMinDailyGoal.text = minGoal.toString()
        }else{
            tvMinDailyGoal.text = "0"
        }

        if (maxGoal != null){
            tvMaxDailyGoal.text = maxGoal.toString()
        }else{
            tvMaxDailyGoal.text = "0"
        }

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

    private fun getDailyGoals(){

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
            if( LocalDate.parse(entry.entryDate)  > startDate &&
                LocalDate.parse(entry.entryDate) < endDate){
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
            tvEndDate.text = String.format("%02d/%02d/%04d", savedDay, savedMonth - 1, savedYear)

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

    private fun getEntriesFromFirestore(){
        db.collection("timeSheetEntries")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val entry = document.toObject(TimesheetEntry::class.java)
                    timesheetEntryList.add(entry)

                    populateRecyclerView(timesheetEntryList)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(ContentValues.TAG, "Error getting documents.", exception)
            }
    }

    private fun getMaxGoal() {
        db.collection("maxGoal").get()
            .addOnSuccessListener { querySnapshot ->
                val document = querySnapshot.documents.firstOrNull()
                if (document != null) {
                    val goal = document.toObject(MaxGoal::class.java)
                    if (goal != null) {
                        Log.d(ContentValues.TAG, "Max goal retrieved: ${goal.value}")
                        maxGoal = goal

                        tvMaxDailyGoal.text = maxGoal.value.toString()

                    } else {
                        Log.d(ContentValues.TAG, "Failed to parse max goal document")
                    }
                } else {
                    Log.d(ContentValues.TAG, "No max goal document found")
                }
            }
            .addOnFailureListener { e ->
                Log.d(ContentValues.TAG, "Failed to retrieve max goal", e)
            }
    }

    private fun getMinGoal() {
        db.collection("minGoal").get()
            .addOnSuccessListener { querySnapshot ->
                val document = querySnapshot.documents.firstOrNull()
                if (document != null) {
                    val goal = document.toObject(MinGoal::class.java)
                    if (goal != null) {
                        Log.d(ContentValues.TAG, "Max goal retrieved: ${goal.value}")
                        minGoal = goal

                        tvMinDailyGoal.text = minGoal.value.toString()

                    } else {
                        Log.d(ContentValues.TAG, "Failed to parse max goal document")
                    }
                } else {
                    Log.d(ContentValues.TAG, "No max goal document found")
                }
            }
            .addOnFailureListener { e ->
                Log.d(ContentValues.TAG, "Failed to retrieve max goal", e)
            }
    }
}