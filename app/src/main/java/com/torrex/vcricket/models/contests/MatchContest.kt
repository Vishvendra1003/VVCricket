package com.torrex.vcricket.models.contests

data class MatchContest(
    val matchId:String="",
    val contestName:String="",
    val contestTeam1Img:String="",
    val contestTeam2Img:String="",
    val contestTeam1Name:String="",
    val contestTeam2Name:String="",
    val contestTeam1BetPrice:Double=0.0,
    val contestTeam2BetPrice:Double=0.0,
    val contestTotalSeat:Int=0,
    val contestSeatLeft:Int=0,
    val contestBetPrice:Int=0
)
