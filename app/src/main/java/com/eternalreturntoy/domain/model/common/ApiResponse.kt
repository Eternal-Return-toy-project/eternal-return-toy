package com.eternalreturntoy.domain.model.common



//TODO Serializable 추가 필요
class ApiResponse<T>(
    val code: String,
    val message: String,
)