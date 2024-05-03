package com.example.timetracktechnology

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SetGoalsActivity : AppCompatActivity() {

    private lateinit var btnSetMinGoal: Button
    private lateinit var btnSetMaxGoal: Button
    private lateinit var btnBack: Button
    private lateinit var tvMinDailyGoal: TextView
    private lateinit var tvMaxDailyGoal: TextView

    private lateinit var myApp: TimeTrackTechnology

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_goals)

        btnSetMinGoal = findViewById(R.id.btnSetMinGoal)
        btnSetMaxGoal = findViewById(R.id.btnSetMaxGoal)
        btnBack = findViewById(R.id.btnBack)
        tvMaxDailyGoal = findViewById<TextView>(R.id.tvMaxGoal)
        tvMinDailyGoal = findViewById<TextView>(R.id.tvMinGoal)

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

        btnSetMinGoal.setOnClickListener(){
            val intent = Intent(this, SetMinGoalActivity::class.java)
            startActivity(intent)
        }

        btnSetMaxGoal.setOnClickListener(){
            val intent = Intent(this, SetMaxGoalActivity::class.java)
            startActivity(intent)
        }

        btnBack.setOnClickListener(){
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }


}