package com.example.timetracktechnology

import android.graphics.Bitmap
import android.text.format.Time
import android.util.Log
import java.time.LocalTime
import java.time.Duration
import java.time.LocalDate


data class TimesheetEntry(
    val id: Int = 0,
    val title: String = "",
    val category: String = "",
    val entryDate: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val imageBitmap: Bitmap? = null
)