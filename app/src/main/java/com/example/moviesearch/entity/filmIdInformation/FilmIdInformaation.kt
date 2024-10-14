package com.example.moviesearch.entity.filmIdInformation

interface FilmIdInformaation {
    val kinopoiskId : Int
    val nameRu : String
    val nameEn : String
    val posterUrl : String
    val posterUrlPreview : String
    val coverUrl : String
    val logoUrl : String
    val ratingKinopoisk : Double
    val webUrl : String
    val year : Int
    val filmLength : Int
    val slogan : String
    val description : String
    val shortDescription : String
    val editorAnnotation : String
    val ratingAgeLimits : String
    val countries : List<Countries>
    val genres : List<Genres>
    val serial : Boolean
}