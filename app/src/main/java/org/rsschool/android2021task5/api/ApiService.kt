package org.rsschool.android2021task5.api

import org.rsschool.android2021task5.model.Image
import retrofit2.http.GET

interface ApiService {

    @GET("images/search")
    suspend fun getImages(): List<Image>

}
