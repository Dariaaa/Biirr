package ru.dariaaa.biirr

import android.app.Application
import ru.dariaaa.biirr.data.di.DbModule
import ru.dariaaa.biirr.di.ApplicationComponent
import ru.dariaaa.biirr.di.DaggerApplicationComponent

class App : Application() {
    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.builder()
            .withApplication(applicationContext)
            .dbModule(DbModule(applicationContext))
            .build()

    }
}