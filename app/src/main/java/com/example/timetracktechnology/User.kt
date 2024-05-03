package com.example.timetracktechnology

import android.provider.ContactsContract.CommonDataKinds.Email

data class User(
    val email: Email,
    val minHours: Int,
    val maxHours: Int
)
