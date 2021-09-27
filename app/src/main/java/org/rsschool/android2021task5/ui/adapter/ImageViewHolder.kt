package org.rsschool.android2021task5.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.rsschool.android2021task5.databinding.GridViewItemBinding
import org.rsschool.android2021task5.model.Image

class ImageViewHolder(private val binding: GridViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Image) {
        itemView.run {

        }
    }

    companion object {
        fun from(parent: ViewGroup) = GridViewItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ).let(::ImageViewHolder)
    }

    private fun <T> views(block: GridViewItemBinding.() -> T): T? = binding.block()
}
