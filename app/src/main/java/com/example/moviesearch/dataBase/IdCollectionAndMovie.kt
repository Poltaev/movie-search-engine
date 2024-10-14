package com.example.moviesearch.dataBase

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "IdCollectionAndMovie",
    primaryKeys = ["collection_id", "movie_id"])
data class IdCollectionAndMovie(
    @ColumnInfo(name = "collection_id")
    val collectionId: Int,
    @ColumnInfo(name = "movie_id")
    val filmId: Int
)
