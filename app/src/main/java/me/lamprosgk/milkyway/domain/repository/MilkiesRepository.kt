package me.lamprosgk.milkyway.domain.repository

import io.reactivex.Single
import me.lamprosgk.milkyway.domain.model.Milky

interface MilkiesRepository {
    fun getImages(searchTerm: String, mediaType: String, startYear: String, endYear: String): Single<List<Milky>>
}