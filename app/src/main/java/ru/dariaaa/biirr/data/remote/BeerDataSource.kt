package ru.dariaaa.biirr.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.withTransaction
import ru.dariaaa.biirr.data.cache.AppDatabase
import ru.dariaaa.biirr.data.cache.model.BeerModel

class BeerDataSource(
    private val db: AppDatabase,
    private val api: IPunkApi,
    private val beerName: String
) : PagingSource<Int, BeerModel>() {


    override fun getRefreshKey(state: PagingState<Int, BeerModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, BeerModel> {
        val key = params.key ?: 1
        val result = api.beers(beerName, key, params.loadSize)
        if (result.isSuccessful) {

            val list = result.body()?.map(BeerModel.Companion::from) ?: emptyList()

            if (list.isNotEmpty()){
                db.withTransaction {
                    db.beerDao().insertAll(list)
                }

            }
            return LoadResult.Page(list, null, key + 1)
        }
        return LoadResult.Error(NoSuchElementException())
    }


}