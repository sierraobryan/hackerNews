package com.example.myapplication.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Item (
    @Json(name = "id") val id: Int,
    @Json(name = "deleted") val isDeleted: Boolean = false,
    @Json(name = "type") val type: String,
    @Json(name = "by") val authorName: String?,
    @Json(name = "time") val publishDate: Int?,
    @Json(name = "dead") val isDead: Boolean = false,
    @Json(name = "kids") val kids: List<Int>?,
    @Json(name = "parent") val parent: Int?,
    @Json(name = "text") val text: String?,
    @Json(name = "url") val url: String?,
    @Json(name = "title") val title: String?,
    @Json(name = "parts") val parts: List<Int>?,
    @Json(name = "score") val score : Int?,
    @Json(name = "descendants") val descendants: Int = 0
    ) : Parcelable