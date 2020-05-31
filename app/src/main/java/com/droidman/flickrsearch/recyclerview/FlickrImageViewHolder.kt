package com.droidman.flickrsearch.recyclerview

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.droidman.flickrsearch.R
import com.droidman.flickrsearch.api.Image

class FlickrImageViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    private val flickrImageView = view.findViewById<ImageView>(R.id.image_view)

    fun bind(image : Image) {
        val flickrImageUrl = "http://farm${image.farm}.static.flickr.com/${image.server}/${image.id}_${image.secret}.jpg"
        flickrImageView.load(flickrImageUrl)
    }
}