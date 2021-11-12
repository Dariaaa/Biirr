package ru.dariaaa.biirr.presentation.di

import dagger.Binds
import dagger.Module
import ru.dariaaa.biirr.presentation.handlers.GlideImageLoader
import ru.dariaaa.biirr.presentation.handlers.ImageLoader
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class PresentationModule {

    @Singleton
    @Binds
    @Named("Glide")
    abstract fun bindImageLoader(imageLoader: GlideImageLoader): ImageLoader


}