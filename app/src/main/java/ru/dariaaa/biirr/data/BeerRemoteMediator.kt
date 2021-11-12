package ru.dariaaa.biirr.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import ru.dariaaa.biirr.data.cache.AppDatabase
import ru.dariaaa.biirr.data.cache.model.BeerModel
import ru.dariaaa.biirr.data.cache.model.RemoteKey
import ru.dariaaa.biirr.data.remote.IPunkApi

@ExperimentalPagingApi
class BeerRemoteMediator(
    private val db: AppDatabase,
    private val api: IPunkApi,
    private val beerName:String? = null
) : RemoteMediator<Int, BeerModel>() {

    val beerDao = db.beerDao()
    val remoteKeyDao = db.remoteKeyDao()

    override suspend fun initialize(): InitializeAction {

        val lastUpdate = remoteKeyDao.remoteKeyByQuery(QUERY_ALL)?.lastUpdate

        lastUpdate ?: return InitializeAction.LAUNCH_INITIAL_REFRESH

        return if (System.currentTimeMillis() - lastUpdate >= CACHE_TIMEOUT) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, BeerModel>): MediatorResult {
        val query = beerName?: QUERY_ALL
        val page = when (loadType) {
            LoadType.REFRESH -> {
                FIRST_PAGE_NUM
            }
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.APPEND -> {
                val remoteKey = remoteKeyDao.remoteKeyByQuery(query)

                if (remoteKey != null && remoteKey.nextKey == null) {
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                }
                remoteKey?.nextKey ?: FIRST_PAGE_NUM
            }
        }


        val response = api.beers(beerName, page, state.config.pageSize)

        if (!response.isSuccessful || response.body() == null) {
            return MediatorResult.Error(NoSuchElementException(response.errorBody().toString()))
        }

        val list = response.body()!!

        db.withTransaction {
            if (loadType == LoadType.REFRESH) {
                remoteKeyDao.deleteByQuery(query)
                beerDao.clearAll()
            }

            remoteKeyDao.insertOrReplace(
                RemoteKey(query, page + 1)
            )

            beerDao.insertAll(list.map(BeerModel.Companion::from))
        }


        return MediatorResult.Success(
            endOfPaginationReached = list.isEmpty()
        )
    }

    companion object {
        const val QUERY_ALL = "query_all"
        const val FIRST_PAGE_NUM = 1
        const val CACHE_TIMEOUT = 1000 * 60 * 60

    }
}
