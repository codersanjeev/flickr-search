package com.droidman.flickrsearch.api

/**
 * data class for JSON response mapping
 */
data class FlickrImageSearchResults(
    val photos: Images,
    val stat: String
)