package com.torrex.vcricket.models.currentMatch

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TeamInfo(
    val img: String,
    val name: String,
    val shortname: String
):Parcelable