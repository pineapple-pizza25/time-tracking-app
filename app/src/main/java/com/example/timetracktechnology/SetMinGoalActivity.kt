package com.example.timetracktechnology

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker

class SetMinGoalActivity : AppCompatActivity() {
    private lateinit var npMaxGoal: NumberPicker
    private lateinit var btnDone: Button
    private lateinit var btnCancel: Button

    private lateinit var myApp: TimeTrackTechnology
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_min_goal)

        myApp = application as TimeTrackTechnology

        npMaxGoal = findViewById(R.id.npHours)
        btnDone = findViewById(R.id.btnDone)
        btnCancel = findViewById(R.id.btnCancel)

        npMaxGoal.minValue = 1
        npMaxGoal.maxValue = 24

        btnCancel.setOnClickListener(){
            val intent = Intent(this, SetGoalsActivity::class.java)
            startActivity(intent)
        }

        btnDone.setOnClickListener(){
            val minGoal = npMaxGoal.value

            myApp.minDailyGoal = minGoal

            val intent = Intent(this, SetGoalsActivity::class.java)
            startActivity(intent)
        }
    }
}