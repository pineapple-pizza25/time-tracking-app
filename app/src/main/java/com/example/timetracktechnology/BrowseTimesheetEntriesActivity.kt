package com.example.timetracktechnology

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BrowseTimesheetEntriesActivity : AppCompatActivity() {

    private  lateinit var btnAddNewEntry: Button
    private lateinit var rcvEntries: RecyclerView

    private lateinit var timesheetEntryList: ArrayList<TimesheetEntry>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_timesheet_entries)

        val myApp = application as TimeTrackTechnology

        rcvEntries = findViewById<RecyclerView>(R.id.rcvTimesheetEntries)
        btnAddNewEntry = findViewById<Button>(R.id.btnAddNewEntry)

        timesheetEntryList = myApp.getTimesheetEntryList()

        btnAddNewEntry.setOnClickListener {
            val intent = Intent(this, CreateTimesheetEntry::class.java)
            startActivity(intent)
        }

        populateRecyclerView()
    }

    private fun populateRecyclerView(){
        try {
            val entriesAdapter = TImesheetEntriesRecyclerViewAdapter(this, timesheetEntryList)
            rcvEntries.adapter = entriesAdapter
            val layoutManager = LinearLayoutManager(this)
            rcvEntries.layoutManager = layoutManager
        }
        catch (exc: Exception) {
            exc.printStackTrace()
        }
    }
}