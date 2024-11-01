package com.example.trabalhokotlincompose.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "filme_table")
data class Filme(
    @PrimaryKey(autoGenerate = true) // autoincrement
    val id: Int = 0,
    val nome: String,
    val ano: String,
    val assistido: Boolean = false
)
