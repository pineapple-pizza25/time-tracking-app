package com.example.timetracktechnology

import android.annotation.SuppressLint
import android.content.ContentValues
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

class LogInActivity : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogIn: Button

    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        edtEmail = findViewById(R.id.edtEmail)
        edtPassword = findViewById(R.id.edtPassword)
        btnLogIn = findViewById(R.id.btnLogin)

        auth = Firebase.auth

        btnLogIn.setOnClickListener {logIn()}
    }

    private fun logIn(){
        var email: String
        var password: String

        email = edtEmail.text.toString()
        password = edtPassword.text.toString()

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this,
                "please enter a valid email address",
                Toast.LENGTH_SHORT
            ).show()
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this,
                "please enter a password",
                Toast.LENGTH_SHORT
            ).show()
        }
        else{
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(ContentValues.TAG, "createUserWithEmail:success")
                        val user = auth.currentUser
                        Toast.makeText(
                            baseContext,
                            "Account Created",
                            Toast.LENGTH_SHORT,
                        ).show()

                        user?.email

                        val intent = Intent(this, MenuActivity::class.java)
                        startActivity(intent)

                    } else {
                        Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
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