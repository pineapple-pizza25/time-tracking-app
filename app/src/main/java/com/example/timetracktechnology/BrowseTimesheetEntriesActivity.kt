package com.example.timetracktechnology

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BrowseTimesheetEntriesActivity : AppCompatActivity() {

    private  lateinit var btnAddNewEntry: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_timesheet_entries)

        val myApp = application as TimeTrackTechnology

        val recyclerView: RecyclerView = findViewById(R.id.rcvTimesheetEntries)
        btnAddNewEntry = findViewById<Button>(R.id.btnAddNewEntry)

        try {
            val entriesAdapter = TImesheetEntriesRecyclerViewAdapter(this, myApp.getTimesheetEntryList())
            recyclerView.adapter = entriesAdapter
            val layoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = layoutManager
        }
        catch (exc: Exception) {
            exc.printStackTrace()
        }

        btnAddNewEntry.setOnClickListener {
            val intent = Intent(this, CreateTimesheetEntry::class.java)
            startActivity(intent)
        }
    }
}