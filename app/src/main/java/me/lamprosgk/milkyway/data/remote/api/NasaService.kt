package me.lamprosgk.milkyway.data.remote.api

import io.reactivex.Single
import me.lamprosgk.milkyway.data.remote.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaService {
    @GET("search")
    fun getImages(@Query("q") searchTerm: String, @Query("media_type") mediaType: String,
                  @Query("year_start") startYear: String, @Query("year_end") endYear: String):
            Single<SearchResponse>
}