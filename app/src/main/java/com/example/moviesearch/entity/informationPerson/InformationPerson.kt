package com.example.moviesearch.entity.informationPerson

interface InformationPerson {
    val personId : Int
    val nameRu : String
    val nameEn : String
    val posterUrl : String
    val posterUrlPreview : String
    val birthday : String
    val age : Int
    val profession : String
    val films : List<Movie>

}