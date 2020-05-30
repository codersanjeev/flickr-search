package com.droidman.flickrsearch.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

fun createFlickrPhotoSearchService() : FlickrPhotoSearchService {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://www.flickr.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    return retrofit.create(FlickrPhotoSearchService::class.java)
}

interface FlickrPhotoSearchService {
    @GET("/services/rest/?method=flickr.photos.search")
    fun searchPhotos(
        @Query(value = "api_key") apiKey : String,
        @Query(value = "text") searchText: String,
        @Query(value = "format") json : String = "json",
        @Query(value = "nojsoncallback") callback: String = "1"
    ) : Call<FlickrPhotoSearchResults>
}