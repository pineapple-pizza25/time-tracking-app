package com.example.timetracktechnology

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class CreateCategoryActivity : AppCompatActivity() {

    private lateinit var btnDone: Button
    private lateinit var edtTitle: EditText
    private lateinit var edtDescription: EditText
    private lateinit var btnBrowseCategories: Button
    val myApp = applicationContext as TimeTrackTechnology

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_category)

        btnDone = findViewById<Button>(R.id.btnDone)
        edtTitle = findViewById<EditText>(R.id.txtTitle)
        edtDescription = findViewById<EditText>(R.id.txtDescription)
        btnBrowseCategories = findViewById<Button>(R.id.btnBrowseCategories)

        btnDone.setOnClickListener { addNewCategory() }

        btnBrowseCategories.setOnClickListener {
            val intent = Intent(this, CreateCategoryActivity::class.java)
            startActivity(intent)
        }
    }

    //Gets the title and description from th edit texts and stores it in an object in the category list
    private fun addNewCategory(){
        val id = myApp.getCategoryId()
        val title = edtTitle.text.toString().trim()
        val description = edtDescription.text.toString().trim()

        val newCategory = Category(id, title, description)
        myApp.addToCategoryList(newCategory)

        val toast = Toast.makeText(this, "Category added!", Toast.LENGTH_SHORT) // in Activity
        toast.show()
    }

}