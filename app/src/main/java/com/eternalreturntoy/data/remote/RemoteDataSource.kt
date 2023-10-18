package com.eternalreturntoy.data.remote

import android.content.Context
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URL
import java.util.Locale

class RemoteDataSource(private val api: EternalReturnAPI, private val context: Context) {
    enum class Language(val value: String) {
        Korean("Korean"),
        English("English"),
        Japanese("Japanese"),
        ChineseSimplified("ChineseSimplified"),
        ChineseTraditional("ChineseTraditional"),
        French("French"),
        Spanish("Spanish"),
        SpanishLatin("SpanishLatin"),
        Portuguese("Portuguese"),
        PortugueseLatin("PortugueseLatin"),
        Indonesian("Indonesian"),
        German("German"),
        Russian("Russian"),
        Thai("Thai"),
        Vietnamese("Vietnamese")
    }

    //사용자 언어 확인
    private fun getCurrentLanguage(): Language {
        val currentLanguage = Locale.getDefault().language

        return when (currentLanguage) {
            "ko" -> Language.Korean
            "en" -> Language.English
            "ja" -> Language.Japanese
            "zh" -> {
                if (Locale.getDefault().country == "CN") Language.ChineseSimplified else Language.ChineseTraditional
            }
            "fr" -> Language.French
            "es" -> {
                if (Locale.getDefault().country == "MX") Language.SpanishLatin else Language.Spanish
            }
            "pt" -> {
                if (Locale.getDefault().country == "BR") Language.PortugueseLatin else Language.Portuguese
            }
            "id" -> Language.Indonesian
            "de" -> Language.German
            "ru" -> Language.Russian
            "th" -> Language.Thai
            "vi" -> Language.Vietnamese
            else -> Language.English  // 기본값으로 영어 설정
        }
    }

    suspend fun downloadAndParseL10nFile(language: Language = getCurrentLanguage()): Map<String, String> {
        val response = api.fetchDataLanguage(language.value)

        val l10nUrl = response.body()?.data?.l10Path ?: return emptyMap()
        val url = URL(l10nUrl)
        val map = mutableMapOf<String, String>()

        withContext(Dispatchers.IO) {
            val content = url.readText()
            val file = File(context.getExternalFilesDir(null), "l10n-${language.value}.txt")
            file.writeText(content)

            // 파일 경로 로그 출력
            Log.d("RemoteDataSource", "파일 저장 위치: ${file.absolutePath}")

            content.lineSequence().forEach { line ->
                val parts = line.split("=")
                if (parts.size == 2) {
                    map[parts[0].trim()] = parts[1].trim()
                }
            }
        }
        Log.d("RemoteDataSource", "L10n 파일에서 ${map.size} 항목 다운로드 및 분석 완료.")

        return map
    }
}