package com.droidman.flickrsearch.view

import com.droidman.flickrsearch.api.Image

/**
 * image list recycler view on click handler interface
 */
interface OnImageClick {
    /**
     * function to be overriden to handle image click
     */
    fun onImageClicked(image : Image)
}