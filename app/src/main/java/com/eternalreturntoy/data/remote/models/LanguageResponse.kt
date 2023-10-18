package com.eternalreturntoy.data.remote.models

data class LanguageResponse(
    val code: Int,
    val message: String,
    val data: L10nData
)

data class L10nData(
    val l10Path: String
)
