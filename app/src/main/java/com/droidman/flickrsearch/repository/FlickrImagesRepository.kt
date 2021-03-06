package com.droidman.flickrsearch.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.droidman.flickrsearch.BuildConfig
import com.droidman.flickrsearch.api.FlickrImageSearchResults
import com.droidman.flickrsearch.api.Image
import com.droidman.flickrsearch.api.createFlickrImageSearchService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

/**
 * gets images from the API using FlickrImageSearchService and
 * stores it in local variable list
 */
class FlickrImagesRepository {

    private val _flickrImagesList = MutableLiveData<List<Image>>()
    val flickrImagesList: LiveData<List<Image>> = _flickrImagesList

    /**
     * searches for images using input text as parameter and
     * populates the flickrImagesList
     */
    fun getFlickrImagesResult(searchText: String) {
        val call = createFlickrImageSearchService().searchFlickrImages(BuildConfig.FLICKR_API_KEY, searchText)
        call.enqueue(object : Callback<FlickrImageSearchResults> {

            override fun onFailure(call: Call<FlickrImageSearchResults>, t: Throwable) {
                Log.e(FlickrImagesRepository::class.java.simpleName, "something went wrong" + t.message)
            }

            override fun onResponse(call: Call<FlickrImageSearchResults>, response: Response<FlickrImageSearchResults>) {
                _flickrImagesList.value = response.body()?.photos?.photo
            }

        })
    }

    /**
     * removes all images from the repository
     */
    fun clearData() {
        _flickrImagesList.value = null
    }
}