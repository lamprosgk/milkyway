package me.lamprosgk.milkyway.data.remote.model


import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("data")
    val info: List<Data>,
    @SerializedName("href")
    val href: String,
    @SerializedName("links")
    val links: List<Link>
)