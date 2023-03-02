package com.torrex.vcricket.models.currentMatch

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Info(
    val cache: Int,
    val credits: Int,
    val hitsLimit: Int,
    val hitsToday: Int,
    val hitsUsed: Int,
    val offsetRows: Int,
    val queryTime: Double,
    val s: Int,
    val server: Int,
    val totalRows: Int
):Parcelable