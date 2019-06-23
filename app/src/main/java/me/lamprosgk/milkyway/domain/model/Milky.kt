package me.lamprosgk.milkyway.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Milky(val center: String, val dateCreated: String, val description: String,
                 val title: String, val nasaId: String, val imageUrl: String): Parcelable

