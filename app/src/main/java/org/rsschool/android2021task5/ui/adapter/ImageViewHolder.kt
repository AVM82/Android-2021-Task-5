package org.rsschool.android2021task5.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import org.rsschool.android2021task5.R
import org.rsschool.android2021task5.databinding.GridViewItemBinding
import org.rsschool.android2021task5.model.ImageDTO

class ImageViewHolder(private val binding: GridViewItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ImageDTO?) {
        item?.let {
            Glide.with(itemView.context)
                .load((it.url))
                .apply(
                    RequestOptions().placeholder(R.drawable.ic_baseline_image_24)
                        .error(R.drawable.ic_baseline_broken_image_24)
                        .centerCrop()
                ).into(binding.image)
        } ?: views {
            image.setImageResource(R.drawable.ic_baseline_cloud_download_24)
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
