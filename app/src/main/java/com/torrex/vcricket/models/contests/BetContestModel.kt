package com.torrex.vcricket.models.contests

import android.accessibilityservice.GestureDescription.StrokeDescription
import java.util.stream.LongStream

data class BetContestModel(
    val matchId:String="",
    val contestId:String="",
    val matchName:String="",
    val matchDate:String="",
    val team1Name:String="",
    val team2Name:String="",
    val team1img:String="",
    val team2img:String="",
    val team1Code:String="",
    val team2Code:String="",
    val team1Score:String="",
    val team2Score:String="",
    val teamSelected:String="",
    val betPrice:Int=0,
    val betTeam1BetRate:Double=0.0,
    val betTeam2BetRate:Double=0.0,
    val matchStatus:String="",
    val userJoinedId:String="",
    val userStatus:String=""

    )
