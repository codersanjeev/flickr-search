package com.droidman.flickrsearch.api

import com.droidman.flickrsearch.utils.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * build flickr image search service
 */
fun createFlickrImageSearchService() : FlickrImageSearchService {
    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    return retrofit.create(FlickrImageSearchService::class.java)
}

/**
 * all available endpoints from flickr API
 */
interface FlickrImageSearchService {
    /**
     * search image using given text endpoint
     */
    @GET(Constants.SEARCH_IMAGES_ENDPOINT)
    fun searchFlickrImages(
        @Query(value = "api_key") apiKey : String,
        @Query(value = "text") searchText: String,
        @Query(value = "format") json : String = "json",
        @Query(value = "nojsoncallback") callback: String = "1"
    ) : Call<FlickrImageSearchResults>
}