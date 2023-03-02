package com.torrex.vcricket.models.currentMatch

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val bbbEnabled: Boolean,
    val date: String,
    val dateTimeGMT: String,
    val fantasyEnabled: Boolean,
    val hasSquad: Boolean,
    val id: String,
    val matchEnded: Boolean,
    val matchStarted: Boolean,
    val matchType: String,
    val name: String,
    val score: List<Score>,
    val series_id: String,
    val status: String,
    val teamInfo: List<TeamInfo>,
    val teams: List<String>,
    val venue: String
):Parcelable