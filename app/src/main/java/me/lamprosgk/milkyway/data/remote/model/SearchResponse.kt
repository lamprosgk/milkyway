package me.lamprosgk.milkyway.data.remote.model


import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("collection")
    val collection: Collection
)