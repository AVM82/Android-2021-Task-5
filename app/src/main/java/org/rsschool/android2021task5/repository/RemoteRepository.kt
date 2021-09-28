package org.rsschool.android2021task5.repository

import org.rsschool.android2021task5.api.ApiService
import org.rsschool.android2021task5.model.ImageDTO
import retrofit2.Response
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val apiService: ApiService) : Repository {

    override suspend fun getImages(): Response<List<ImageDTO>> = apiService.getImages()
}