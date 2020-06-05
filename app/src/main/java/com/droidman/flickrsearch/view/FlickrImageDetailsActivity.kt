package com.droidman.flickrsearch.view

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.webkit.URLUtil
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import coil.api.load
import com.droidman.flickrsearch.R
import com.droidman.flickrsearch.api.Image
import com.droidman.flickrsearch.utils.Constants
import com.droidman.flickrsearch.utils.Utility
import okhttp3.internal.Util

class FlickrImageDetailsActivity : AppCompatActivity() {

    private lateinit var image : Image
    private lateinit var detailsImageView : ImageView
    private lateinit var downloadButton : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flickr_image_details)
        detailsImageView = findViewById(R.id.details_image_view)
        downloadButton = findViewById(R.id.download_image_button)
        image = intent.extras?.get(Constants.IMAGE_DETAILS_KEY) as Image
        detailsImageView.load(Utility.getImageURL(image))
    }

    fun shareImage(view: View) {
        val imageUrl = Utility.getImageURL(image)
        val intent : Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "check out this image of ${image.title} from here : $imageUrl")
            type = "text/plain"
        }
        startActivity(Intent.createChooser(intent, "Share Image"))
    }

    fun downloadImage(view: View) {
        val imageUrl = Utility.getImageURL(image)
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val imageUri = Uri.parse(imageUrl)
        val downloadRequest = DownloadManager.Request(imageUri)
        val fileName = URLUtil.guessFileName(imageUrl, null, null)
        downloadRequest.apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            setTitle(fileName)
            setDescription("downloading $fileName")
            setVisible(true)
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            Toast.makeText(this@FlickrImageDetailsActivity, "downloading image...", Toast.LENGTH_LONG).show()
            downloadManager.enqueue(this)
            downloadButton.isEnabled = false
        }
    }

}