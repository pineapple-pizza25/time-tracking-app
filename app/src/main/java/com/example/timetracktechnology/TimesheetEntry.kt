package com.example.timetracktechnology

import java.time.LocalTime
import java.time.LocalDate

data class TimesheetEntry(
    val id: Int,
    val title: String,
    val categoryId: Int,
    val entryDate: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime
)
