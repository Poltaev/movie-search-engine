package com.example.moviesearch.data

import com.example.moviesearch.entity.filmIdInformation.Countries
import com.example.moviesearch.entity.filmIdInformation.FilmIdInformaation
import com.example.moviesearch.entity.filmIdInformation.Genres

class FilmIdInformationDto(
    override val kinopoiskId: Int,
    override val nameRu: String,
    override val nameEn: String,
    override val posterUrl: String,
    override val posterUrlPreview: String,
    override val coverUrl: String,
    override val logoUrl: String,
    override val ratingKinopoisk: Double,
    override val webUrl: String,
    override val year: Int,
    override val filmLength: Int,
    override val slogan: String,
    override val description: String,
    override val shortDescription: String,
    override val editorAnnotation: String,
    override val ratingAgeLimits: String,
    override val countries: List<Countries>,
    override val genres: List<Genres>,
    override val serial: Boolean
) : FilmIdInformaation