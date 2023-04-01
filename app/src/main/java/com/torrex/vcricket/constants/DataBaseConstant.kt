package com.torrex.vcricket.constants

object DataBaseConstant {


    //Table Name
    const val USERS:String="users"
    const val TRANSACTION_DATA="transactionData"
    const val USER_TRANSACTION_DATA="userTransactionData"
    const val USER_FUND="userFund"
    const val MATCH_CONTEST="matchContest"
    const val MY_JOINED_CONTEST:String="my_joined_contest"


    //variable for the FireBase Storage for User
    const val ID:String="id"
    const val EMAIL:String="email"
    const val FIRSTNAME:String="firstName"
    const val LASTNAME:String="lastName"
    const val MOBILE:String="mobile"
    const val GENDER:String="gender"
    const val DOB:String="dob"
    const val IMAGE:String="image"
    const val PROFILECOMPLETE:String="profileCompleted"
    const val USERACCOUNT:String="userAccount"

    //Variable for the Firebase Storage for User fund
    const val USERFUND:String="userFund"
    const val USERID:String="userId"

    //constant for status of Transaction
    const val CANCELLED:String="Cancelled"
    const val SUCCESS:String="Success"
    const val FAILED:String="Failed"
    const val DEPOSIT:String="Deposit"
    const val WITHDRAW:String="WithDraw"

    //constant for the field of match Contest
    const val MATCH_ID:String="matchId"
    const val CONTEST_ID:String="contestId"
    const val BET_RATE_TEAM_1="contestTeam1BetPrice"
    const val BET_RATE_TEAM_2="contestTeam2BetPrice"
    const val BET_CONTEST_SEAT_LEFT="contestSeatLeft"
    const val BET_CONTEST_TOTAL_SEAT="contestTotalSeat"

    //Constant for the field of MyJoinedContest
    const val JOINED_CONTEST_ID:String="joinedContestId"
    const val USER_JOINED_ID:String="userJoinedId"
    const val CONTEST_ID_JOINED_CONTEST_ID:String="contestId"


}