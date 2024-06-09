package com.example.timetracktechnology

data class Category (
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val maxGoal: Int? = null,
    val minGoal: Int? = null
)