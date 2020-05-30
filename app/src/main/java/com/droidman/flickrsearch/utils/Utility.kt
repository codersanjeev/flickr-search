package com.droidman.flickrsearch.utils

import android.content.Context
import android.content.res.Configuration

class Utility {
    companion object {
        fun isLandscape(context : Context) : Boolean {
            return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        }
    }
}