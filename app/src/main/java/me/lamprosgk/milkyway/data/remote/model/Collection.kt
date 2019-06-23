package me.lamprosgk.milkyway.data.remote.model


import com.google.gson.annotations.SerializedName

data class Collection(
    @SerializedName("href")
    val href: String,
    @SerializedName("items")
    val items: List<Item>,
    @SerializedName("metadata")
    val metadata: Metadata,
    @SerializedName("version")
    val version: String
)