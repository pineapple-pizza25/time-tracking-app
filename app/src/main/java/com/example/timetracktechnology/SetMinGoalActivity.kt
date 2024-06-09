package com.example.timetracktechnology

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class SetMinGoalActivity : AppCompatActivity() {

    val db = Firebase.firestore

    private lateinit var minGoal: MinGoal

    private lateinit var npMinGoal: NumberPicker
    private lateinit var btnDone: Button
    private lateinit var btnCancel: Button

    private lateinit var myApp: TimeTrackTechnology
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_min_goal)

        myApp = application as TimeTrackTechnology

        npMinGoal = findViewById(R.id.npHours)
        btnDone = findViewById(R.id.btnDone)
        btnCancel = findViewById(R.id.btnCancel)

        npMinGoal.minValue = 1
        npMinGoal.maxValue = 24

        cancelButton()

        minGoalButton()
    }

    private fun cancelButton(){
        btnCancel.setOnClickListener(){
            val intent = Intent(this, SetGoalsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun minGoalButton(){
        btnDone.setOnClickListener(){

            deleteMinGoal()

            val intent = Intent(this, SetGoalsActivity::class.java)
            startActivity(intent)
        }
    }

    /*
   deletes current min goal in db
    */
    private fun deleteMinGoal() {
        db.collection("minGoal").get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete()
                        .addOnSuccessListener {
                            Log.d(ContentValues.TAG, "Deleted min Goal")
                        }
                        .addOnFailureListener { exception ->
                            Log.d(ContentValues.TAG, "Failed to delete min Goal", exception)
                            val toast = Toast.makeText(this, "Failed to set min goal ${exception.message} ", Toast.LENGTH_SHORT)
                            toast.show()
                        }
                }

                val goal = npMinGoal.value
                minGoal = MinGoal(goal)
                setMinGoal(minGoal)
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Failed to retrieve min Goal")
                val toast = Toast.makeText(this, "Failed to set min goal ${exception.message} ", Toast.LENGTH_SHORT)
                toast.show()
            }
    }

    /*
    sets min goal in db
     */
    private fun setMinGoal(minGoal: MinGoal){
        db.collection("minGoal")
            .add(minGoal)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "min Goal set")
            }
            .addOnFailureListener { e ->
                Log.d(ContentValues.TAG, "Failed to set min Goal")

                val toast = Toast.makeText(this, "Failed to set min goal ${e.message} ", Toast.LENGTH_SHORT)
                toast.show()
            }
    }
}