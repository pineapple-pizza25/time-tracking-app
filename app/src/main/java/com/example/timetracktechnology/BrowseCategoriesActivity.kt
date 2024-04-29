package com.example.timetracktechnology

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class BrowseCategoriesActivity : AppCompatActivity() {

    private lateinit var lvCategories: ListView
    val myApp = applicationContext as TimeTrackTechnology

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_browse_categories)

        lvCategories = findViewById<ListView>(R.id.lvCategories)
    }

    private fun populateListView(){
        val categories = myApp.getCategoryList()
        val adapter = ArrayAdapter<Category>(this, android.R.layout.simple_list_item_1, categories)
        lvCategories.adapter = adapter
    }
}