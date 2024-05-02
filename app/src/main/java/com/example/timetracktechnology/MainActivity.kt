package com.example.timetracktechnology

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {

    private lateinit var btnLogIn: Button
    private lateinit var btnSignUp: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLogIn = findViewById<Button>(R.id.btnLogin)
        btnSignUp = findViewById<Button>(R.id.btnSignUp)

        btnLogIn.setOnClickListener() {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }

    }


}