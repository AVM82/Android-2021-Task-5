package org.rsschool.android2021task5.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import org.rsschool.android2021task5.model.ImageDTO
import org.rsschool.android2021task5.paging.ImageRemotePagingSource

interface Repository {

    suspend fun fetchImagesFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<ImageDTO>>


    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(
            pageSize = ImageRemotePagingSource.DEFAULT_PAGE_SIZE,
            enablePlaceholders = false
        )
    }

}
