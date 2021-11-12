package ru.dariaaa.biirr.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import dagger.BindsInstance
import dagger.Component
import ru.dariaaa.biirr.MainActivity
import ru.dariaaa.biirr.data.di.DbModule
import ru.dariaaa.biirr.data.di.NetworkModule
import ru.dariaaa.biirr.presentation.details.BeerDetailsFragment
import ru.dariaaa.biirr.presentation.di.AdapterModule
import ru.dariaaa.biirr.presentation.di.PresentationModule
import ru.dariaaa.biirr.presentation.di.ViewModelModule
import ru.dariaaa.biirr.presentation.list.BeerListFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        DbModule::class,
        PresentationModule::class,
        AdapterModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun withApplication(application: Context): Builder

        fun build(): ApplicationComponent

        fun dbModule(module: DbModule): Builder
    }


    fun inject(mainActivity: MainActivity?)

    @ExperimentalPagingApi
    fun inject(beerListFragment: BeerListFragment?)

    fun inject(beerDetailsFragment: BeerDetailsFragment?)
}