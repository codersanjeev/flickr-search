package com.droidman.flickrsearch.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.droidman.flickrsearch.R
import com.droidman.flickrsearch.api.Image
import com.droidman.flickrsearch.utils.Constants
import com.droidman.flickrsearch.utils.Utility
import com.droidman.flickrsearch.view.OnImageClick

/**
 * List adapter for Image Search Results
 */
class FlickrImagesAdapter(val imageClickListener : OnImageClick) : ListAdapter<Image, FlickrImageViewHolder>(DIFF_CONFIG) {

    companion object {
        val DIFF_CONFIG = object : DiffUtil.ItemCallback<Image>() {
            override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlickrImageViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_flickr_image_view, parent, false)
        val availableWidth = parent.measuredWidth
        val layoutParams = itemView.layoutParams
        layoutParams.width = availableWidth / if (Utility.isLandscapeMode(parent.context)) Constants.IMAGES_PER_ROW_IN_LANDSCAPE else Constants.IMAGES_PER_ROW_IN_PORTRAIT
        layoutParams.height = layoutParams.width
        itemView.layoutParams = layoutParams
        return FlickrImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FlickrImageViewHolder, position: Int) {
        holder.bind(getItem(position), imageClickListener)
    }
}