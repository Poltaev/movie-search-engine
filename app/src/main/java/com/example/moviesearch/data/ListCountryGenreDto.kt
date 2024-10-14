package com.example.moviesearch.data

import com.example.moviesearch.entity.listContryGenre.IdCountry
import com.example.moviesearch.entity.listContryGenre.IdGenre
import com.example.moviesearch.entity.listContryGenre.ListIdCountryGenre

class ListCountryGenreDto(
    override val countries: List<IdCountry>,
    override val genres: List<IdGenre>
) : ListIdCountryGenre