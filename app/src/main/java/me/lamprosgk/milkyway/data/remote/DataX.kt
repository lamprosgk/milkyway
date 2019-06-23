package me.lamprosgk.milkyway.data.remote

import me.lamprosgk.milkyway.data.remote.model.Data
import java.util.*

val Data.dateCreatedPretty: String
    get() {
        val originalFormat = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val targetFormat = java.text.SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date = originalFormat.parse(this.dateCreated)
        return targetFormat.format(date)
    }
