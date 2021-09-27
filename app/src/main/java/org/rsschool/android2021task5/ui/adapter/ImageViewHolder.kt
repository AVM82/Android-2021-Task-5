package org.rsschool.android2021task5.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.rsschool.android2021task5.R
import org.rsschool.android2021task5.databinding.GridViewItemBinding
import org.rsschool.android2021task5.model.Image

class ImageViewHolder(private val binding: GridViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Image) {
        itemView.run {

        }
        Glide.with(itemView.context)
            .load((item.url))
            .apply(
                RequestOptions().placeholder(R.drawable.ic_baseline_image_24)
                    .error(R.drawable.ic_baseline_broken_image_24)
                    .centerInside()
            ).into(binding.image)
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
