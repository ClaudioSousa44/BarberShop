package com.cauhsousa.barbershop.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_user")
data class User(
    @PrimaryKey (autoGenerate = true) var id: Long = 0,
    @ColumnInfo(name = "user_name") var userName: String = "",
    val phone: String = "",
    val email: String = "",
    val password: String = ""
)
