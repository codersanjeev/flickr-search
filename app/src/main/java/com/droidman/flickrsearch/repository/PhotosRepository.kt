package com.droidman.flickrsearch.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.droidman.flickrsearch.BuildConfig
import com.droidman.flickrsearch.MainActivity
import com.droidman.flickrsearch.api.FlickrPhotoSearchResults
import com.droidman.flickrsearch.api.Photo
import com.droidman.flickrsearch.api.createFlickrPhotoSearchService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhotosRepository {

    private val _photosList = MutableLiveData<List<Photo>>()
    val photosList: LiveData<List<Photo>> = _photosList

    fun getPhotos(searchText: String) {
        val call = createFlickrPhotoSearchService().searchPhotos(BuildConfig.FLICKR_API_KEY, searchText)
        call.enqueue(object : Callback<FlickrPhotoSearchResults> {
            override fun onFailure(call: Call<FlickrPhotoSearchResults>, t: Throwable) {
                Log.e(MainActivity::class.java.simpleName, "error loading", t)
            }
            override fun onResponse(call: Call<FlickrPhotoSearchResults>, response: Response<FlickrPhotoSearchResults>) {
                val results = response.body()
                val photos = results?.photos?.photo
                _photosList.value = photos
            }
        })
    }

    fun clearData() {
        _photosList.value = null
    }

}