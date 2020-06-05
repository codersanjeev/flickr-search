package com.droidman.flickrsearch.api

import java.io.Serializable

/**
 * data class for JSON response mapping
 */
data class Image(
    val farm: Int,
    val id: String,
    val isfamily: Int,
    val isfriend: Int,
    val ispublic: Int,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String
) : Serializable