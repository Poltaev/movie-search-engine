package com.example.moviesearch.data

import com.example.moviesearch.entity.filmIdInformation.Countries
import com.example.moviesearch.entity.filmIdInformation.FilmIdInformaation
import com.example.moviesearch.entity.filmIdInformation.Genres
import com.example.moviesearch.entity.informationPerson.InformationPerson
import com.example.moviesearch.entity.informationPerson.Movie

class PersonInformationDto(
    override val age: Int,
    override val birthday: String,
    override val films: List<Movie>,
    override val nameEn: String,
    override val nameRu: String,
    override val personId: Int,
    override val posterUrl: String,
    override val posterUrlPreview: String,
    override val profession: String,

) : InformationPerson