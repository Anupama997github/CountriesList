package com.example.countrieslist.mvvm.model

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.countrieslist.network.CountriesListApiService
import retrofit2.HttpException
import java.io.IOException

private const val DEFAULT_PAGE_INDEX = 1

class CountriesPagingSource (
    val service: CountriesListApiService
) : PagingSource<Int, Country>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Country> {
        //for first case it will be null, then we can pass some default value, in our case it's 1
        return try {
            // Key may be null during a refresh, if no explicit key is passed into Pager
            // construction. Use 0 as default, because our API is indexed started at index 0
            val pageNumber = params.key ?: DEFAULT_PAGE_INDEX

            // Suspending network load via Retrofit. This doesn't need to be wrapped in a
            // withContext(Dispatcher.IO) { ... } block since Retrofit's Coroutine
            // CallAdapter dispatches on a worker thread.
            val response = service.fetchCountriesList(pageNumber, params.loadSize)
            Log.d(TAG, "Response from load() : $response")
            // Since 0 is the lowest page number, return null to signify no more pages should
            // be loaded before it.
            val prevKey = if (pageNumber > 0) pageNumber - 1 else null

            // This API defines that it's out of data when a page returns empty. When out of
            // data, we return `null` to signify no more pages should be loaded
            val nextKey = if (response.isNotEmpty()) pageNumber + 1 else null
            Log.d("TAG", "PrevKey=$prevKey, NextKey=$nextKey")
            return LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            Log.e(TAG, "IOException" + e.localizedMessage)
            LoadResult.Error(e)
        } catch (e: HttpException) {
            Log.e(TAG, "HttpException"  + e.localizedMessage)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Country>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    companion object {
        private val TAG = CountriesPagingSource::class.qualifiedName
    }
}