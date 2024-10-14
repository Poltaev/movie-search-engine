package com.example.moviesearch.data

import com.example.moviesearch.entity.SerialSeries.InformationSerialSeries
import com.example.moviesearch.entity.SerialSeries.Season

class SeasonSerialDto (
    override val total: Int,
    override val items: List<Season>
) : InformationSerialSeries