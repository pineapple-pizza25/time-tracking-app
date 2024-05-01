package com.example.timetracktechnology

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class BrowseCategoriesActivity : AppCompatActivity() {

    private lateinit var btnAddNewCategory: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_categories)

        val myApp = application as TimeTrackTechnology

        val recyclerView: RecyclerView = findViewById(R.id.rcvCategories)
        btnAddNewCategory = findViewById<Button>(R.id.btnAddNewCategory)

        try {
            val categoriesAdapter = CategoriesRecyclerViewAdapter(this, myApp.getCategoryList())
            recyclerView.adapter = categoriesAdapter
            val layoutManager = LinearLayoutManager(this)
            recyclerView.layoutManager = layoutManager
        }
        catch (exc: Exception) {
            exc.printStackTrace()
        }

        btnAddNewCategory.setOnClickListener {
            val intent = Intent(this, CreateCategoryActivity::class.java)
            startActivity(intent)
        }
    }


}