package com.eternalreturntoy.data.remote.models

data class DataResponse(
    val code: Int,
    val message: String,
    val data: Map<String, Long>
)