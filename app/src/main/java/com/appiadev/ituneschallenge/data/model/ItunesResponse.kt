package com.appiadev.ituneschallenge.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ItunesResponse {
    @SerializedName("resultCount")
    @Expose
    var resultCount = 0

    @SerializedName("results")
    @Expose
    var results: List<Result>? = null
}