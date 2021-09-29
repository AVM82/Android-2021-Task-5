package org.rsschool.android2021task5.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import org.rsschool.android2021task5.api.ApiService
import org.rsschool.android2021task5.model.ImageDTO
import retrofit2.HttpException

class ImageRemotePagingSource(private val apiService: ApiService) :
    PagingSource<Int, ImageDTO>() {
    override fun getRefreshKey(state: PagingState<Int, ImageDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageDTO> {
        try {
            val page = params.key ?: INITIAL_PAGE_NUMBER
            val pageSize = params.loadSize.coerceAtMost(MAX_PAGE_SIZE)
            val response = apiService.getImages(page, pageSize)
            return if (response.isSuccessful) {
                val images = response.body().orEmpty()
                val nextPageNumber = if (images.isEmpty()) null else page + 1
                val prevPageNumber = if (page > 1) page - 1 else null
                LoadResult.Page(
                    data = images,
                    prevKey = prevPageNumber,
                    nextKey = nextPageNumber
                )
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
        const val MAX_PAGE_SIZE = 20
        const val INITIAL_PAGE_NUMBER = 1
    }
}
