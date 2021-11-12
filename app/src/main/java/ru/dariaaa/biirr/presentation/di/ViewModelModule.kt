package ru.dariaaa.biirr.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.dariaaa.biirr.presentation.details.BeerDetailsViewModel
import ru.dariaaa.biirr.presentation.list.BeerListViewModel

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(
        factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(BeerListViewModel::class)
    internal abstract fun bindBeerListViewModel(viewModel: BeerListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BeerDetailsViewModel::class)
    internal abstract fun bindBeerDetailsViewModel(viewModel: BeerDetailsViewModel): ViewModel


}