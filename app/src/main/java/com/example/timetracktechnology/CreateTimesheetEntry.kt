package com.example.timetracktechnology

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.LocalDate
import java.time.YearMonth

class CreateTimesheetEntry : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    val db = Firebase.firestore

    private val PERMISSION_REQUEST_CODE = 200
    private val IMAGE_SELECTION_REQUEST_CODE = 201

    private lateinit var categoryList: ArrayList<Category>
    private lateinit var categoryTitles: ArrayList<String>

    /*
    variables that temporarily store dates and times
    before they are written to the db
     */
    private lateinit var startTime: LocalTime
    private lateinit var endTime: LocalTime
    private lateinit var entryDate: LocalDate

    private var categoryId: Int = 0

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var savedDay = 0
    private var savedMonth = 0
    private var savedYear = 0
    private var savedHour = 0
    private var savedMinute = 0


    private lateinit var tvTitle: TextView
    private lateinit var edtTitle: EditText
    private lateinit var tvCategory: TextView
    private lateinit var spnCategory: Spinner
    private lateinit var tvStartTime: TextView
    private lateinit var tvEndTime: TextView
    private lateinit var btnDone: Button
    private lateinit var tvDate: TextView
    private  lateinit var btnBrowseEntries: Button
    private  lateinit var btnAddImageFromGallery: Button
    private  lateinit var btnTakePhoto: Button
    private lateinit var imageView: ImageView

    private var selectedImageBitmap: Bitmap? = null

    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_timesheet_entry)

        categoryList = ArrayList<Category>()
        categoryTitles = ArrayList<String>()

        val timeTrackApp = applicationContext as TimeTrackTechnology

        tvTitle = findViewById<TextView>(R.id.tvTitle)
        edtTitle = findViewById<EditText>(R.id.edtTitle)
        tvCategory = findViewById<TextView>(R.id.tvCategory)
        spnCategory = findViewById<Spinner>(R.id.spnCategory)
        tvEndTime = findViewById<TextView>(R.id.tvEndTime)
        btnDone = findViewById<Button>(R.id.btnDone)
        tvStartTime= findViewById<TextView>(R.id.tvStartTime)
        tvDate = findViewById<TextView>(R.id.tvDate)
        btnBrowseEntries = findViewById<Button>(R.id.btnBrowseEntries)
        btnAddImageFromGallery = findViewById<Button>(R.id.btnAddImageFromGallery)
        btnTakePhoto = findViewById<Button>(R.id.btnTakePhoto)
        imageView = findViewById(R.id.imageView)

        registerResult()
        pickDateAndTime()

        getCategoriesFromFirestore()

        createCategoryTitleList()




        /*
        Add a new time sheet entry to firestore
         */
        fun addNewTimesheetEntry(startTime: LocalTime, endTime: LocalTime, entryDate: LocalDate,
                                 image: Bitmap?) {
            val id = timeTrackApp.getTimesheetEntryId()
            val title = edtTitle.text.toString().trim()
            val categoryItem = spnCategory.selectedItem as Category
            val category = categoryItem.title

            val newTimesheetEntry =
                TimesheetEntry(id, title, category, entryDate.toString(),
                    startTime.toString(), endTime.toString(), image)
            timeTrackApp.addToTimesheetEntryList(newTimesheetEntry)

            db.collection("timeSheetEntries")
                .add(newTimesheetEntry)
                .addOnSuccessListener { documentReference ->
                    Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "Error adding document", e)
                }
        }

        btnDone.setOnClickListener() {

            addNewTimesheetEntry(startTime, endTime, entryDate, selectedImageBitmap)

            Toast.makeText(
                applicationContext,
                "New Timesheet Entry Added!",
                Toast.LENGTH_SHORT
            ).show()

            val intent = Intent(this, BrowseTimesheetEntriesActivity::class.java)
            startActivity(intent)
        }

        btnAddImageFromGallery.setOnClickListener {
            selectImageFromGallery()
        }

        btnTakePhoto.setOnClickListener(){
            cameraImage()
        }

        btnBrowseEntries.setOnClickListener(){
            val intent = Intent(this, BrowseTimesheetEntriesActivity::class.java)
            startActivity(intent)
        }
    }

    /*
    sets up click listeners for date and time pickers
     */
    @SuppressLint("SetTextI18n")
    private fun pickDateAndTime() {
        tvStartTime.setOnClickListener {
            getDateTimeCalendar()

            TimePickerDialog(this, startTimeListener, hour, minute, true).show()
        }

        tvEndTime.setOnClickListener {
            getDateTimeCalendar()

            TimePickerDialog(this, endTimeListener, hour, minute, true).show()
        }

        tvDate.setOnClickListener{
            getDateTimeCalendar()

            DatePickerDialog(this, dateClickListener, year, month, day ).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

    }


    /*
    sets the click listener for the select start time textView
     */
    private val startTimeListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        savedHour = hourOfDay
        savedMinute = minute
        getDateTimeCalendar()

        startTime = LocalTime.of(savedHour, savedMinute)
        tvStartTime.text = String.format("%02d:%02d", savedHour, savedMinute)
    }

    /*
    sets the click listener for the select end time textView
     */
    private val endTimeListener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        savedHour = hourOfDay
        savedMinute = minute
        getDateTimeCalendar()

        endTime = LocalTime.of(savedHour, savedMinute)
        tvEndTime.text = String.format("%02d:%02d", savedHour, savedMinute)
    }

    /*
    sets the click listener for the select date textView
     */
    private val dateClickListener = DatePickerDialog.OnDateSetListener { _, year , month, day ->
        savedDay = day
        savedMonth = month + 1
        savedYear = year
        getDateTimeCalendar()

        val yearMonth = YearMonth.of(savedYear, savedMonth)
        if (yearMonth.isValidDay(savedDay)) {

            getDateTimeCalendar()
            entryDate = LocalDate.of(savedYear, savedMonth, savedDay)
            tvDate.text = String.format("%02d/%02d/%04d", savedDay, savedMonth - 1, savedYear)

        } else {

            Toast.makeText(this,
                "Please select a valid date",
                Toast.LENGTH_SHORT
            ).show()

        }
    }

    private fun getDateTimeCalendar(){
        val currenDateTime = LocalDateTime.now()
        day = currenDateTime.dayOfMonth
        month = currenDateTime.monthValue
        year = currenDateTime.year
        hour = currenDateTime.hour
        minute = currenDateTime.minute
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        TODO("Not yet implemented")
    }


    /*
    Allows user to pick an image from the gallery
     */
    private fun selectImageFromGallery() {
        // Start image selection intent
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_SELECTION_REQUEST_CODE)
    }

    /*
    Handles result of selectImageFromGallery function
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_SELECTION_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // Image selected successfully
            val imageUri: Uri? = data?.data
            imageView.setImageURI(imageUri)
            selectedImageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        }
    }

    /*
    Launches camera for the user to select an image
     */
    private fun cameraImage(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        resultLauncher.launch(intent)
    }

    /*
    Does something with the selected image
     */
    private fun registerResult(){
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                fun onActivityResult(result: ActivityResult){
                    try{
                        val imageUri: Uri? = result.data?.data
                        imageView.setImageURI(imageUri)
                        selectedImageBitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                    }
                    catch (e: Exception){
                        Toast.makeText(this,"No image selected",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )
    }


    /*
    places all documents of the category collection in the db
    into the categoryList array list
     */
    private fun getCategoriesFromFirestore(){
        db.collection("categories")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val category = document.toObject(Category::class.java)
                    categoryList.add(category)
                }

                createCategoryTitleList()

                val categoriesAdapter = CategoriesSpinnerAdapter(
                    applicationContext,
                    categoryList
                )

                spnCategory.adapter = categoriesAdapter
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }


    }

    private fun createCategoryTitleList(){
        for (category in categoryList){
            categoryTitles.add(category.title)
        }
    }
}







