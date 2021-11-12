package ru.dariaaa.biirr.presentation.handlers

import android.widget.ImageView

interface ImageLoader {
    fun loadDrawable(imageView: ImageView, url: String)
}