package ru.dariaaa.biirr.presentation.di

import dagger.Module
import dagger.Provides
import ru.dariaaa.biirr.presentation.handlers.ImageLoader
import ru.dariaaa.biirr.presentation.list.BeerListAdapter
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [PresentationModule::class])
class AdapterModule {

    @Singleton
    @Provides
    fun provideBeerAdapter(@Named("Glide") imageLoader: ImageLoader) = BeerListAdapter(imageLoader)

}