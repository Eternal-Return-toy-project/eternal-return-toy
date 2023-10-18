package com.eternalreturntoy.data.remote.models

data class FreeCharacters(
    val code: Int,
    val message: String,
    val freeCharacters: List<Long>
)
