package org.rsschool.android2021task5.helper

import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import org.rsschool.android2021task5.R

fun getDefaultRequestOptions() = RequestOptions()
    .placeholder(R.drawable.loading_animation)
    .error(R.drawable.ic_baseline_broken_image_24)
    .fallback(R.drawable.ic_baseline_photo_camera_24)
    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
