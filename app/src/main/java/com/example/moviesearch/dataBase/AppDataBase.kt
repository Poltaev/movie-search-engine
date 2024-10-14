package com.example.moviesearch.dataBase

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ViewedMovie::class,
        HistoryMovieActor::class,
        IndexForWeKnewAboutFirstUse::class,
        Collection::class,
        FilmForCollection::class,
        IdCollectionAndMovie::class], version = 1
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}