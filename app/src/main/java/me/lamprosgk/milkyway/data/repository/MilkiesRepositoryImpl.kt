package me.lamprosgk.milkyway.data.repository

import me.lamprosgk.milkyway.data.mapper.Mapper
import me.lamprosgk.milkyway.data.remote.api.NasaService
import me.lamprosgk.milkyway.data.remote.model.Item
import me.lamprosgk.milkyway.domain.model.Milky
import me.lamprosgk.milkyway.domain.repository.MilkiesRepository

class MilkiesRepositoryImpl constructor(private val nasaService: NasaService, private val mapper: Mapper<Item, Milky>) :
    MilkiesRepository {
    override fun getImages(searchTerm: String, mediaType: String, startYear: String, endYear: String) =
        nasaService.getImages(searchTerm, mediaType, startYear, endYear).map {
            mapper.mapTo(it.collection.items) }
}