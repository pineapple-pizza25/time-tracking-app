package com.example.timetracktechnology

import java.sql.Time
import java.time.LocalDateTime
import java.util.Date

data class TimesheetEntry(
    val id: Int,
    val title: String,
    val categoryId: Int,
    val entryDate: Date,
    val startTime: Date,
    val endTime: Date
)
