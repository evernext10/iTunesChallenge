package com.appiadev.ituneschallenge.data.repository

import com.appiadev.ituneschallenge.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getSongs() = apiHelper.getSong()
}