package com.example.moviesearch.dataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface MovieDao {
    @Query("SELECT * FROM viewedMovieAndSerial")
    fun getAllViewedMoviesFlow(): kotlinx.coroutines.flow.Flow<List<ViewedMovie>>

    @Query("SELECT * FROM viewedMovieAndSerial")
    fun getAllViewedMoviesList(): List<ViewedMovie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertViewedMovies(movie: ViewedMovie)

    @Delete
    suspend fun deleteViewedMovies(movie: ViewedMovie)

    @Update
    suspend fun updateViewedMovies(movie: ViewedMovie)

    @Query("SELECT * FROM historyMovieActor")
    fun getAllActorMoviesFlow(): kotlinx.coroutines.flow.Flow<List<HistoryMovieActor>>

    @Query("SELECT * FROM historyMovieActor")
    fun getAllActorMoviesList(): List<HistoryMovieActor>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertActorMovies(movie: HistoryMovieActor)

    @Delete
    suspend fun deleteActorMovies(movie: HistoryMovieActor)

    @Update
    suspend fun updateActorMovies(movie: HistoryMovieActor)

    @Query("SELECT * FROM collectionMovieAndSerial")
    fun getAllCollectionFlow(): kotlinx.coroutines.flow.Flow<List<Collection>>

    @Query("SELECT * FROM collectionMovieAndSerial")
    fun getAllCollectionList(): List<Collection>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCollection(collection: Collection)

    @Delete
    suspend fun deleteCollection(collection: Collection)

    @Update
    suspend fun updateCollection(collection: Collection)

    @Query("SELECT * FROM saved_film")
    fun getAllSavedMovieFlow(): kotlinx.coroutines.flow.Flow<List<FilmForCollection>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSavedMovie(film: FilmForCollection)

    @Delete
    suspend fun deleteSavedMovie(film: FilmForCollection)

    @Update
    suspend fun updateSavedMovie(film: FilmForCollection)

    @Transaction
    @Query("SELECT * FROM IdCollectionAndMovie")
    fun getAllCollectionWithFilmFlow(): kotlinx.coroutines.flow.Flow<List<IdCollectionAndMovie>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMovieFromCollection(film: IdCollectionAndMovie)

    @Delete
    suspend fun deleteMovieFromCollection(film: IdCollectionAndMovie)

    @Update
    suspend fun updateMovieFromCollection(film: IdCollectionAndMovie)

    @Query("SELECT * FROM indexForWeKnewAboutFirstUse")
    fun getIndexFirstUseFlow(): kotlinx.coroutines.flow.Flow<List<IndexForWeKnewAboutFirstUse>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun loadIndexFirstUse(index : IndexForWeKnewAboutFirstUse)
    @Update
    suspend fun updateIndex(index: IndexForWeKnewAboutFirstUse)
}