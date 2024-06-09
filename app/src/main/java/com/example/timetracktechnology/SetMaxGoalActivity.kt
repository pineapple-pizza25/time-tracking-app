package com.example.timetracktechnology

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class SetMaxGoalActivity : AppCompatActivity() {

    val db = Firebase.firestore

    private lateinit var maxGoal: MaxGoal

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

        cancelButton()

        maxGoalButton()
    }

    private fun cancelButton(){
        btnCancel.setOnClickListener(){
            val intent = Intent(this, SetGoalsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun maxGoalButton(){
        btnDone.setOnClickListener(){

            deleteMaxGoal()

            val intent = Intent(this, SetGoalsActivity::class.java)
            startActivity(intent)
        }
    }

    /*
    deletes current max goal in db
     */
    private fun deleteMaxGoal(){

        db.collection("maxGoal").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete()
                        .addOnSuccessListener {
                            Log.d(TAG, "Deleted maxGoal")
                        }
                        .addOnFailureListener { exception ->
                            Log.d(TAG, "Failed to delete maxGoal")
                        }
                }

                val goal = npMaxGoal.value
                maxGoal = MaxGoal(goal)
                setMaxGoal(maxGoal)
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "Failed to retrieve maxGoal")
            }

       // return flag
    }

    /*
    sets min goal in db
     */
    private fun setMaxGoal(maxGoal: MaxGoal){
        db.collection("maxGoal")
            .add(maxGoal)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "maxGoal set")
            }
            .addOnFailureListener { e ->
                Log.d(TAG, "Failed to set maxGoal")
            }
    }
}