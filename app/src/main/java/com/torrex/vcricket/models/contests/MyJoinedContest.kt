package com.torrex.vcricket.models.contests

data class MyJoinedContest(
    val matchId:String="",
    val contestId:String="",
    val matchName:String="",
    val matchDate:String="",
    val team1Name:String="",
    val team2Name:String="",
    val team1img:String="",
    val team2img:String="",
    val team1Score:String="0/1",
    val team2Score:String="0/2",
    val teamSelected:Int=0,
    val betPrice:Int=0,
    val betTeam1BetRate:Double=0.0,
    val betTeam2BetRate:Double=0.0,
    val matchStatus:String="",
    val userJoinedId:String="",
    val userStatus:String=""

)

