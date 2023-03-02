package com.torrex.vcricket.models.currentMatch

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CurrentMatchModel(
    val apikey: String,
    val `data`: List<Data>,
    val info: Info,
    val status: String
):Parcelable