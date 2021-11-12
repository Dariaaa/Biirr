package ru.dariaaa.biirr.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.dariaaa.biirr.data.cache.AppDatabase
import javax.inject.Singleton

@Module
class DbModule(private val applicationContext: Context) {

    private val db = AppDatabase.buildDatabase(applicationContext)

    @Singleton
    @Provides
    fun providesDb() = db
    @Singleton
    @Provides
    fun providesBeerDao() = db.beerDao()
    @Singleton
    @Provides
    fun providesRemoteKeyDao() = db.remoteKeyDao()



}