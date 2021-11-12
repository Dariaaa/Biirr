package ru.dariaaa.biirr.data.cache

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.dariaaa.biirr.data.cache.dao.BeerDao
import ru.dariaaa.biirr.data.cache.dao.RemoteKeyDao
import ru.dariaaa.biirr.data.cache.model.BeerModel
import ru.dariaaa.biirr.data.cache.model.RemoteKey

@Database(entities = [BeerModel::class, RemoteKey::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun beerDao(): BeerDao
    abstract fun remoteKeyDao(): RemoteKeyDao

    companion object {
         fun buildDatabase(appContext: Context) = Room.databaseBuilder(
                appContext,
                AppDatabase::class.java, "beer-db"
            )
                .build()
    }
}

