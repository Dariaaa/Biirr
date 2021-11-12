package ru.dariaaa.biirr.data.di

import androidx.paging.ExperimentalPagingApi
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.dariaaa.biirr.BuildConfig
import ru.dariaaa.biirr.data.BeerRemoteMediator
import ru.dariaaa.biirr.data.cache.AppDatabase
import ru.dariaaa.biirr.data.remote.IPunkApi
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetworkModule {

    @ExperimentalPagingApi
    @Singleton
    @Provides
    fun providesRemoteMediator(db: AppDatabase, api:IPunkApi) = BeerRemoteMediator(db, api)

    @Singleton
    @Provides
    fun providePunkApiService(okHttpClient: OkHttpClient): IPunkApi {
        val retrofit =  Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(IPunkApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(CONNECT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return logging
    }

    companion object {
        private const val CONNECT_TIMEOUT_SECONDS = 120L
        private const val READ_TIMEOUT_SECONDS = 120L
    }
}