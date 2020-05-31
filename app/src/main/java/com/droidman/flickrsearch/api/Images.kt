package com.droidman.flickrsearch.api

data class Images(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val photo: List<Image>,
    val total: String
)