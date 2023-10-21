package com.eternalreturntoy.data.remote.models

data class GamesResponse(
    val next: Int,
    val code: Int,
    val userGames: List<UserGame>,
    val message: String
)

data class UserGame(
    val userNum: Int,
    val nickname: String,
    val masterLevel: Any
)

//data class MasterLevel(

//)