package org.rsschool.android2021task5.repository

import org.rsschool.android2021task5.model.ImageDTO
import retrofit2.Response

interface Repository {

    suspend fun getImages(): Response<List<ImageDTO>>

}
