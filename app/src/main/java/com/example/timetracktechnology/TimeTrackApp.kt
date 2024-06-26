package com.example.timetracktechnology

import android.app.Application

class TimeTrackTechnology: Application() {
    private lateinit var categoryList: ArrayList<Category>
    private lateinit var timesheetEntryList: ArrayList<TimesheetEntry>

    var minDailyGoal: Int? = null
    var maxDailyGoal: Int? = null

    lateinit var user: User

    override fun onCreate(){
        super.onCreate()
        categoryList = ArrayList<Category>()
        timesheetEntryList = ArrayList<TimesheetEntry>()

        populateCatergoryListDefaults()
    }

    fun getCategoryList(): ArrayList<Category>{ return categoryList }

    fun addToCategoryList(category: Category){ categoryList.add(category) }

    fun getTimesheetEntryList(): ArrayList<TimesheetEntry>{ return timesheetEntryList }

    fun addToTimesheetEntryList(entry: TimesheetEntry){ timesheetEntryList.add(entry) }

    fun getCategoryId(): Int{return timesheetEntryList.size}

    fun getTimesheetEntryId(): Int{return timesheetEntryList.size}

    private fun populateCatergoryListDefaults(){
        categoryList += Category(0,"Chores","Household tasks", null, null)
        categoryList += Category(1,"Homework","Work to do from School/College/Work", null, null)
    }
}