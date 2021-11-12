package ru.dariaaa.biirr.presentation.handlers

import android.widget.ImageView
import com.bumptech.glide.Glide
import ru.dariaaa.biirr.R
import javax.inject.Inject

class GlideImageLoader @Inject constructor() : ImageLoader{

    override fun loadDrawable(imageView: ImageView, url: String) {
        Glide
            .with(imageView)
            .load(url)
            .placeholder(R.drawable.placeholder)
            .into(imageView)
    }
}