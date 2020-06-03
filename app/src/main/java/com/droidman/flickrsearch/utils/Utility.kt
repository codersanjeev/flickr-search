package com.droidman.flickrsearch.utils

import android.content.Context
import android.content.res.Configuration

/**
 * class containing all utility static functions
 */
class Utility {
    companion object {
        /**
         * checks if device is in landscape mode or not
         */
        fun isLandscapeMode(context : Context) : Boolean {
            return context.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        }
    }
}