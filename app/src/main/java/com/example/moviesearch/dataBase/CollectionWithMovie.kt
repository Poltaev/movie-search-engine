package com.example.moviesearch.dataBase

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CollectionWithMovie(
    @Embedded
    val collection : Collection,
    @Relation(
        entity = FilmForCollection::class,
        parentColumn = "id",
        entityColumn = "film_id",
        associateBy = Junction(
            IdCollectionAndMovie::class,
            parentColumn = "collection_id",
            entityColumn = "movie_id"
        )
    )
    val filmForCollection: List<FilmForCollection>
)
