package me.lamprosgk.milkyway.data.remote.model


import com.google.gson.annotations.SerializedName

data class Metadata(
    @SerializedName("total_hits")
    val totalHits: Int
)