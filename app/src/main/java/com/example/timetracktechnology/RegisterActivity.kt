package com.example.timetracktechnology

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class RegisterActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnRegister: Button

    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = Firebase.auth

        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {register()}
    }

    private fun register() {
        var email: String
        var password: String

        email = edtEmail.text.toString()
        password = edtPassword.text.toString()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(
                this,
                "please enter a valid email address",
                Toast.LENGTH_SHORT
            ).show()
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(
                this,
                "please enter a password",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {

                        Log.d(TAG, "createUserWithEmail:success")
                        val user = auth.currentUser

                        Toast.makeText(
                            baseContext,
                            "Account Created",
                            Toast.LENGTH_SHORT,
                        ).show()

                        val intent = Intent(this, LogInActivity::class.java)
                        startActivity(intent)

                    } else {

                        Log.w(TAG, "createUserWithEmail:failure", task.exception)

                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()

                    }
                }
        }
    }
}