package com.droidman.flickrsearch.api

/**
 * data class for JSON response mapping
 */
data class Images(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: List<Image>,
    val total: String
)