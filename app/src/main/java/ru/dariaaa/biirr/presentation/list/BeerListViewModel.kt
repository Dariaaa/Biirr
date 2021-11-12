package ru.dariaaa.biirr.presentation.list

import androidx.lifecycle.*
import androidx.paging.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import ru.dariaaa.biirr.data.BeerRemoteMediator
import ru.dariaaa.biirr.data.cache.AppDatabase
import ru.dariaaa.biirr.data.cache.model.BeerModel
import ru.dariaaa.biirr.data.remote.IPunkApi
import javax.inject.Inject

class BeerListViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var db: AppDatabase

    @Inject
    lateinit var api: IPunkApi

    var searchLiveData: LiveData<PagingData<BeerModel>>? = null
    protected val scope = CoroutineScope(Dispatchers.IO)

    val mediatorLiveData: MediatorLiveData<PagingData<BeerModel>> = MediatorLiveData()

    @ExperimentalPagingApi
    fun source(): LiveData<PagingData<BeerModel>> {

        searchLiveData?.let {
            mediatorLiveData.removeSource(it)
            searchLiveData = null
        }

        val remoteMediator = BeerRemoteMediator(db, api)
        val pager = Pager(
            config = PagingConfig(pageSize = PAGE_SIZE, initialLoadSize = INITIAL_LOAD_SIZE),
            initialKey = INITIAL_KEY,
            remoteMediator = remoteMediator,
            pagingSourceFactory = { remoteMediator.beerDao.pagingSource() }
        )
        return pager.flow.cachedIn(scope).asLiveData().distinctUntilChanged()

    }

    fun destroy() {
        scope.cancel()
    }


    companion object {
        private const val PAGE_SIZE = 10
        private const val INITIAL_KEY = 1
        private const val INITIAL_LOAD_SIZE = PAGE_SIZE * 3
        private const val CONNECT_TIMEOUT_SECONDS = 120L
        private const val READ_TIMEOUT_SECONDS = 120L
    }
}