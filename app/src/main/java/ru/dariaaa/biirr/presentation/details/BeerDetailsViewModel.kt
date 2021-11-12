package ru.dariaaa.biirr.presentation.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import ru.dariaaa.biirr.data.cache.dao.BeerDao
import ru.dariaaa.biirr.data.cache.model.BeerModel
import javax.inject.Inject

class BeerDetailsViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var beerDao: BeerDao

    protected val state = MutableLiveData<BeerModel>()

    protected val scope = CoroutineScope(Dispatchers.IO)


    fun state() = state


    fun load(id: Int) {

        scope.launch {
            val beer = beerDao.getBeerById(id)
            state.postValue(beer)
        }
    }

    fun destroy() {
        scope.cancel()
    }
}