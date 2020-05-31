package com.droidman.flickrsearch.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.droidman.flickrsearch.R
import com.droidman.flickrsearch.api.Photo
import com.droidman.flickrsearch.utils.Constants
import com.droidman.flickrsearch.utils.Utility

class FlickrImagesAdapter() : ListAdapter<Photo, PhotoViewHolder>(DIFF_CONFIG) {

    companion object {
        val DIFF_CONFIG = object : DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        val availableWidth = parent.measuredWidth
        val layoutParams = itemView.layoutParams
        layoutParams.width = availableWidth / if (Utility.isLandscape(parent.context)) Constants.IMAGES_PER_ROW_IN_LANDSCAPE else Constants.IMAGES_PER_ROW_IN_PORTRAIT
        layoutParams.height = layoutParams.width
        itemView.layoutParams = layoutParams
        return PhotoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}