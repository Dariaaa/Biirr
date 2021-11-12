package ru.dariaaa.biirr.data.cache.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.dariaaa.biirr.data.cache.model.BeerModel

@Dao
interface BeerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<BeerModel>)

    //    @Query("SELECT * FROM beers WHERE na LIKE :query")
//    fun pagingSource(query: String): PagingSource<Int, BeerDao>
    @Query("SELECT * FROM beers where id = :id")
    fun getBeerById(id: Int): BeerModel

    @Query("SELECT * FROM beers")
    fun pagingSource(): PagingSource<Int, BeerModel>

    @Query("DELETE FROM beers")
    suspend fun clearAll()
}