package ru.dariaaa.biirr.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.dariaaa.biirr.domain.entity.Beer

interface IPunkApi {

    @GET("v2/beers")
    suspend fun beers(
        @Query("beer_name") name: String?,
        @Query("page") page: Int,
        @Query("per_page") prePage: Int
    ): Response<List<Beer>>

    @GET("v2/beers")
    suspend fun search(

        @Query("page") page: Int,
        @Query("per_page") prePage: Int
    ): Response<List<Beer>>

}