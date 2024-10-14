package com.example.moviesearch.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "indexForWeKnewAboutFirstUse")
data class IndexForWeKnewAboutFirstUse(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val firstUse: String = "First"
)
