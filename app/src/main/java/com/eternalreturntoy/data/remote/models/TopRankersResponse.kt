package com.eternalreturntoy.data.remote.models

data class TopRankersResponse(
    val rankers: List<Ranker>,
    val message: String
)

data class Ranker(
    val userNum: Int,
    val nickname: String,
    val rank: Int,
)