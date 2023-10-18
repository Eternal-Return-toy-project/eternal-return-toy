package com.eternalreturntoy.data.remote

import com.eternalreturntoy.data.remote.models.DataResponse
import com.eternalreturntoy.data.remote.models.FreeCharacters
import com.eternalreturntoy.data.remote.models.GamesResponse
import com.eternalreturntoy.data.remote.models.LanguageResponse
import com.eternalreturntoy.data.remote.models.TopRankersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EternalReturnAPI {
    @GET("/v1/rank/top/{seasonId}/{matchingTeamMode}")
    suspend fun fetchTopRankers(
        @Path("seasonId") seasonId: String,
        @Path("matchingTeamMode") matchingTeamMode: String,
    ): Response<TopRankersResponse>


    @GET("/v1/data/{metaType}")
    suspend fun fetchDataByMetaType(
        @Path("metaType") metaType: String,
    ): Response<DataResponse>

    @GET("/v1/games/{gameId}")
    suspend fun fetchUserGamesByGameId(
        @Path("gameId") gameId: String,
        @Query("next") next: String?
    ): Response<GamesResponse>

    @GET("/v1/freeCharacters/{matchingMode}")
    suspend fun fetchFreeCharacter(
        @Path("matchingMode") matchingMode: String
    ): Response<FreeCharacters>

    @GET("/v1/l10n/{language}")
    suspend fun fetchDataLanguage(
        @Path("language") dataLanguage: String
    ): Response<LanguageResponse>

}