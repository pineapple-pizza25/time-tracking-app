package com.example.timetracktechnology

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker

class SetMaxGoalActivity : AppCompatActivity() {

    private lateinit var npMaxGoal: NumberPicker
    private lateinit var btnDone: Button
    private lateinit var btnCancel: Button

    private lateinit var myApp: TimeTrackTechnology
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_max_goal)

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
            val maxGoal = npMaxGoal.value

            myApp.maxDailyGoal = maxGoal

            val intent = Intent(this, SetGoalsActivity::class.java)
            startActivity(intent)
        }
    }
}