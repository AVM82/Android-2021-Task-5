package org.rsschool.android2021task5.model

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image(
    @Json(name = "id")
    private val id: String,
    @Json(name = "url")
    private val url: String,
    @Json(name = "height")
    private val height: Int,
    @Json(name = "width")
    private val width: Int
) : Parcelable
