package org.rsschool.android2021task5.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import org.rsschool.android2021task5.model.ImageDTO

object DiffCallBack : DiffUtil.ItemCallback<ImageDTO>() {
    override fun areItemsTheSame(oldItem: ImageDTO, newItem: ImageDTO): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ImageDTO, newItem: ImageDTO): Boolean =
        oldItem == newItem
}
