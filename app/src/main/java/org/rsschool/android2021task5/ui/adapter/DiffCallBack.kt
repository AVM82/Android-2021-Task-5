package org.rsschool.android2021task5.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import org.rsschool.android2021task5.model.Image

object DiffCallBack : DiffUtil.ItemCallback<Image>() {
    override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean = oldItem == newItem
}
