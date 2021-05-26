package com.appiadev.ituneschallenge.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("search")
    suspend fun getSongFromITunes(
        @Query("term") term : String,
        @Query("mediaType") type : String,
        @Query("limit") limit : Int
    ): ItunesResponse

}