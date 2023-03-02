package com.torrex.vcricket.models.currentMatch

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Score(
    val inning: String,
    val o: Double,
    val r: Int,
    val w: Int
):Parcelable