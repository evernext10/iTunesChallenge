package com.appiadev.ituneschallenge.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Result {
    @SerializedName("wrapperType")
    @Expose
    var wrapperType: String? = null

    @SerializedName("artistType")
    @Expose
    var artistType: String? = null

    @SerializedName("artistName")
    @Expose
    var artistName: String? = null

    @SerializedName("artistLinkUrl")
    @Expose
    var artistLinkUrl: String? = null

    @SerializedName("artistId")
    @Expose
    var artistId: Long? = null

    @SerializedName("amgArtistId")
    @Expose
    var amgArtistId: Long? = null

    @SerializedName("primaryGenreName")
    @Expose
    var primaryGenreName: String? = null

    @SerializedName("primaryGenreId")
    @Expose
    var primaryGenreId: Long? = null
}