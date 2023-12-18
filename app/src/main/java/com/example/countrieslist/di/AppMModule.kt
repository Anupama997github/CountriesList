package com.example.countrieslist.di

import com.example.countrieslist.mvvm.repository.CountriesRepository
import com.example.countrieslist.mvvm.repository.CountriesRepositoryImp
import com.example.countrieslist.network.CountriesListApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideOkHttpClinent() = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .build()

    companion object {
        const val BASE_URL = "https://gist.githubusercontent.com/peymano-wmt/32dcb892b06648910ddd40406e37fdab/raw/"
        const val TIMEOUT = 30L // In Secs
    }

    @Provides
    @Singleton
    fun provideCountriesListApiService(retrofit: Retrofit): CountriesListApiService {
        return retrofit.create(CountriesListApiService::class.java)
    }

    @Provides
    @Singleton
    fun providesCountryRepository(countriesListApiService: CountriesListApiService): CountriesRepository {
        return CountriesRepositoryImp(countriesListApiService)
    }
}