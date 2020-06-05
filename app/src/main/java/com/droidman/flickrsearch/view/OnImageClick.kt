package com.droidman.flickrsearch.view

import com.droidman.flickrsearch.api.Image

interface OnImageClick {
    fun onImageClicked(image : Image)
}