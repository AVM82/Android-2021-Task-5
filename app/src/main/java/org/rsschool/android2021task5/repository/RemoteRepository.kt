package org.rsschool.android2021task5.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.rsschool.android2021task5.api.ApiService
import org.rsschool.android2021task5.model.ImageDTO
import org.rsschool.android2021task5.paging.ImageRemotePagingSource
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val apiService: ApiService) : Repository {

    override fun fetchImagesFlow(pagingConfig: PagingConfig): Flow<PagingData<ImageDTO>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { ImageRemotePagingSource(apiService) }
        ).flow
    }
}
