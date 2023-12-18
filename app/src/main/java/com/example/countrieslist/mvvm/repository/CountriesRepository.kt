package com.example.countrieslist.mvvm.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.countrieslist.mvvm.model.Country

interface CountriesRepository {
    suspend fun getAllCountries(): LiveData<PagingData<Country>>
}