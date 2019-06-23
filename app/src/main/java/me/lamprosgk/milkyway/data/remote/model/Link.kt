package me.lamprosgk.milkyway.data.remote.model


import com.google.gson.annotations.SerializedName

data class Link(
    @SerializedName("href")
    val url: String,
    @SerializedName("rel")
    val rel: String,
    @SerializedName("render")
    val render: String
)