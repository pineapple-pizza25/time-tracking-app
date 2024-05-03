package com.example.timetracktechnology

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MenuActivity : AppCompatActivity() {

    private lateinit var btnBrowseCategories: Button
    private lateinit var btnBrowseTimesheetEntries: Button
    private lateinit var btnGoals: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btnBrowseCategories = findViewById(R.id.btnBrowseCategories)
        btnBrowseTimesheetEntries = findViewById(R.id.btnBrowseTimesheetEntries)
        btnGoals = findViewById(R.id.btnSetDailyGoals)

        btnBrowseTimesheetEntries.setOnClickListener{
            val intent = Intent(this, BrowseTimesheetEntriesActivity::class.java)
            startActivity(intent)
        }

        btnBrowseCategories.setOnClickListener{
            val intent = Intent(this, BrowseCategoriesActivity::class.java)
            startActivity(intent)
        }

        btnGoals.setOnClickListener{
            val intent = Intent(this, SetGoalsActivity::class.java)
            startActivity(intent)
        }
    }
}