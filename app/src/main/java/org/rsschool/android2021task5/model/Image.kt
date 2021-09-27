package org.rsschool.android2021task5.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    @Json(name = "id")
    val id: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "height")
    val height: Int,
    @Json(name = "width")
    val width: Int
) : Parcelable
