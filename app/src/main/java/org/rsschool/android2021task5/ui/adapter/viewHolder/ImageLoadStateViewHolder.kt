package org.rsschool.android2021task5.ui.adapter.viewHolder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import org.rsschool.android2021task5.databinding.ImageLoadStateFooterViewItemBinding

class ImageLoadStateViewHolder(
    private val binding: ImageLoadStateFooterViewItemBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        views {
            retryButton.setOnClickListener { retry.invoke() }
        }
    }


    fun bind(loadState: LoadState) {

        if (loadState is LoadState.Error) {
            views {
                loadErrorMsg.text = loadState.error.localizedMessage
            }
        }
        views {
            loadProgressBar.visibility = toVisibility(loadState is LoadState.Loading)
            retryButton.visibility = toVisibility(loadState !is LoadState.Loading)
            loadErrorMsg.visibility = toVisibility(loadState !is LoadState.Loading)
        }
    }

    private fun toVisibility(constraint: Boolean): Int = if (constraint) {
        View.VISIBLE
    } else {
        View.GONE
    }

    companion object {
        fun from(parent: ViewGroup, retry: () -> Unit): ImageLoadStateViewHolder =
            ImageLoadStateFooterViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ).let { ImageLoadStateViewHolder(it, retry) }
    }

    private fun <T> views(block: ImageLoadStateFooterViewItemBinding.() -> T): T? = binding.block()
}
