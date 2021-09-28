package org.rsschool.android2021task5.ui.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import org.rsschool.android2021task5.ui.adapter.viewHolder.ImageLoadStateViewHolder
import org.rsschool.android2021task5.ui.adapter.viewHolder.ImageViewHolder

class ImagesLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<ImageLoadStateViewHolder>() {


    override fun onBindViewHolder(holder: ImageLoadStateViewHolder, loadState: LoadState) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ImageLoadStateViewHolder {
        TODO("Not yet implemented")
    }
}