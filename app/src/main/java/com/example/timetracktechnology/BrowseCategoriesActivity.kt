package com.example.timetracktechnology

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class BrowseCategoriesActivity : AppCompatActivity() {

    val db = Firebase.firestore

    private lateinit var btnAddNewCategory: Button
    private lateinit var btnMenu: Button
    private lateinit var categoryList: ArrayList<Category>
    private lateinit var recyclerView: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_categories)

        categoryList = ArrayList<Category>()

       recyclerView = findViewById(R.id.rcvCategories)
        btnAddNewCategory = findViewById<Button>(R.id.btnAddNewCategory)
        btnMenu = findViewById<Button>(R.id.btnMenu)

        db.collection("categories")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val category = document.toObject(Category::class.java)
                    categoryList.add(category)
                    val toast = Toast.makeText(this, "Here are your categories", Toast.LENGTH_SHORT)
                    toast.show()

                   populateRecyclerView(categoryList)
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
                val toast = Toast.makeText(this, "Unable to get categories", Toast.LENGTH_SHORT)
                toast.show()
            }


   /*     try {
            val categoriesAdapter = CategoriesRecyclerViewAdapter(this, categoryList)
            recyclerView.adapter = categoriesAdapter
            val layoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = layoutManager
        }
        catch (exc: Exception) {
            exc.printStackTrace()
        } */

        btnAddNewCategory.setOnClickListener {
            val intent = Intent(this, CreateCategoryActivity::class.java)
            startActivity(intent)
        }

        btnMenu.setOnClickListener{
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    private fun populateRecyclerView(categories: ArrayList<Category>){
        try {
            val categoriesAdapter = CategoriesRecyclerViewAdapter(this, categories)
            recyclerView.adapter = categoriesAdapter
            val layoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = layoutManager
        }
        catch (exc: Exception) {
            exc.printStackTrace()
        }
    }


}