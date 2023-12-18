package com.app.countrieslist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.countrieslist.mvvm.model.Country
import com.example.countrieslist.mvvm.repository.CountriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CountriesListViewModel @Inject constructor(
    private val countriesRepository: CountriesRepository
) : ViewModel() {

    private val _countries = MutableLiveData<PagingData<Country>>()

    suspend fun getCountriesList(): LiveData<PagingData<Country>> {
                val response = countriesRepository.getAllCountries().cachedIn(viewModelScope)
                _countries.value = response.value
                return response
    }
}