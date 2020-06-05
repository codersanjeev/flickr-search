package com.droidman.flickrsearch.recyclerview

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.droidman.flickrsearch.R
import com.droidman.flickrsearch.api.Image
import com.droidman.flickrsearch.utils.Utility
import com.droidman.flickrsearch.view.OnImageClick
import okhttp3.internal.Util

/**
 * single image view holder
 */
class FlickrImageViewHolder(view : View) : RecyclerView.ViewHolder(view) {

    private val flickrImageView = view.findViewById<ImageView>(R.id.image_view)

    /**
     * populates data in a image cell
     */
    fun bind(image : Image, imageClickListener : OnImageClick) {
        itemView.setOnClickListener {
            imageClickListener.onImageClicked(image)
        }
        flickrImageView.load(Utility.getImageURL(image))
    }
}