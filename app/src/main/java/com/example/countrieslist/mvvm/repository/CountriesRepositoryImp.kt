package com.example.countrieslist.mvvm.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.countrieslist.mvvm.model.CountriesPagingSource
import com.example.countrieslist.mvvm.model.Country
import com.example.countrieslist.network.CountriesListApiService
import javax.inject.Inject

class CountriesRepositoryImp @Inject constructor(
    private val countriesApiService : CountriesListApiService
) : CountriesRepository {
    override suspend fun getAllCountries(): LiveData<PagingData<Country>> {
        val pagingSourceFactory = { CountriesPagingSource(countriesApiService) }
        Log.d(TAG, " In getAllCountries()")
        return Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            pagingSourceFactory =  pagingSourceFactory
        ).liveData
    }

    companion object {
        private val TAG = CountriesRepositoryImp::class.qualifiedName
    }

}