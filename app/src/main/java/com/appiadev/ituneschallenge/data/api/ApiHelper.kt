package com.appiadev.ituneschallenge.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getSong() = apiService.getSongFromITunes("in+utero","music",20)
}