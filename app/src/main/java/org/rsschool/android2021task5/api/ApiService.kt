package org.rsschool.android2021task5.api

import androidx.annotation.IntRange
import org.rsschool.android2021task5.model.ImageDTO
import org.rsschool.android2021task5.paging.ImageRemotePagingSource.Companion.DEFAULT_PAGE_SIZE
import org.rsschool.android2021task5.paging.ImageRemotePagingSource.Companion.MAX_PAGE_SIZE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("images/search")
    suspend fun getImages(
        @Query("limit") @IntRange(
            from = 1,
            to = MAX_PAGE_SIZE.toLong()
        ) limit: Int = DEFAULT_PAGE_SIZE,
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("order") order: Order = Order.RAND
    ): Response<List<ImageDTO>>

    enum class Order {
        DESC,
        ASC,
        RAND
    }

}
