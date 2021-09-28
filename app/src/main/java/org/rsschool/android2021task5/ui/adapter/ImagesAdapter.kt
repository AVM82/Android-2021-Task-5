package org.rsschool.android2021task5.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import org.rsschool.android2021task5.model.ImageDTO

class ImagesAdapter : ListAdapter<ImageDTO, ImageViewHolder>(DiffCallBack) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder =
        ImageViewHolder.from(parent)

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) =
        holder.bind(getItem(position))
}