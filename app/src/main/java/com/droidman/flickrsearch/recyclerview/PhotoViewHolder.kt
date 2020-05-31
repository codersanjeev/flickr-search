package com.droidman.flickrsearch.recyclerview

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.droidman.flickrsearch.R
import com.droidman.flickrsearch.api.Photo

class PhotoViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    private val flickrPhotoImageView = view.findViewById<ImageView>(R.id.image_view)

    fun bind(photo : Photo) {
        val flickrPhotoUrl = "http://farm${photo.farm}.static.flickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg"
        flickrPhotoImageView.load(flickrPhotoUrl)
    }
}