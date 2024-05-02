package com.example.timetracktechnology

import android.app.Application

class TimeTrackTechnology: Application() {
    private lateinit var categoryList: ArrayList<Category>
    private lateinit var timesheetEntryList: ArrayList<TimesheetEntry>
    private val minDailyGoal: Int? = null
    private val maxDailyGoal: Int? = null


    override fun onCreate(){
        super.onCreate()
        categoryList = ArrayList<Category>()
        timesheetEntryList = ArrayList<TimesheetEntry>()
    }

    fun getCategoryList(): ArrayList<Category>{ return categoryList }

    fun addToCategoryList(category: Category){ categoryList.add(category) }

    fun getTimesheetEntryList(): ArrayList<TimesheetEntry>{ return timesheetEntryList }

    fun addToTimesheetEntryList(entry: TimesheetEntry){ timesheetEntryList.add(entry) }

    fun getCategoryId(): Int{return timesheetEntryList.size}

    fun getTimesheetEntryId(): Int{return timesheetEntryList.size}
}