package com.droidman.flickrsearch

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.droidman.flickrsearch.api.Image
import com.droidman.flickrsearch.recyclerview.FlickrImagesAdapter
import com.droidman.flickrsearch.repository.FlickrImagesRepository
import com.droidman.flickrsearch.utils.Constants
import com.droidman.flickrsearch.utils.Utility
import com.droidman.flickrsearch.view.FlickrImageDetailsActivity
import com.droidman.flickrsearch.view.OnImageClick

/**
 * Main activity of the application
 */
class FlickrMainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, OnImageClick {

    private lateinit var flickrSearchView : SearchView
    private lateinit var flickrImagesSearchingProgressBar : ProgressBar
    private lateinit var flickrImagesSearchingTextView : TextView
    private lateinit var flickrTextView : TextView
    private lateinit var flickrImageView : ImageView
    private lateinit var flickrImagesRecyclerView : RecyclerView

    private lateinit var flickrImagesAdapter : FlickrImagesAdapter
    private var flickrImagesRepository = FlickrImagesRepository()

    private var flickrQueryString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
        setupRecyclerView()
        setupObservers()
    }

    override fun onDestroy() {
        flickrImagesRepository.flickrImagesList.removeObservers(this)
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        flickrSearchView = menu.findItem(R.id.search).actionView as SearchView
        flickrSearchView.setOnQueryTextListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            handleBackButtonPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        flickrQueryString = query
        hideRecyclerViewAndShowProgressBar()
        flickrImagesRepository.getFlickrImagesResult(query)
        flickrImagesSearchingTextView.text = "looking for $query"
        flickrSearchView.setQuery("", false)
        flickrSearchView.isIconified = true
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        return false
    }

    private fun setupViews() {
        flickrImagesRecyclerView = findViewById(R.id.flickr_images_list_view)
        flickrImagesSearchingProgressBar = findViewById(R.id.searching_progress_bar)
        flickrImagesSearchingTextView = findViewById(R.id.searching_text_view)
        flickrTextView = findViewById(R.id.flickr_text_view)
        flickrImageView = findViewById(R.id.flickr_image_view)
    }

    private fun setupRecyclerView() {
        val flickrImagesPerRow = if (Utility.isLandscapeMode(this)) Constants.IMAGES_PER_ROW_IN_LANDSCAPE else Constants.IMAGES_PER_ROW_IN_PORTRAIT
        flickrImagesRecyclerView.layoutManager = GridLayoutManager(this, flickrImagesPerRow)
        flickrImagesAdapter = FlickrImagesAdapter(this)
        flickrImagesRecyclerView.adapter = flickrImagesAdapter
    }

    private fun setupObservers() {
        val flickrImagesObserver = Observer<List<Image>> { images ->
            flickrImagesAdapter.submitList(images)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.title = "results for $flickrQueryString"
            showRecyclerView()
        }
        flickrImagesRepository.flickrImagesList.observe(this, flickrImagesObserver)
    }

    private fun showRecyclerView() {
        flickrImagesSearchingTextView.visibility = View.GONE
        flickrImagesSearchingProgressBar.visibility = View.GONE
        flickrImageView.visibility = View.GONE
        flickrTextView.visibility = View.GONE
        flickrImagesRecyclerView.visibility = View.VISIBLE
    }

    private fun hideRecyclerViewAndShowProgressBar() {
        flickrImagesRecyclerView.visibility = View.GONE
        flickrImageView.visibility = View.GONE
        flickrTextView.visibility = View.GONE
        flickrImagesSearchingTextView.visibility = View.VISIBLE
        flickrImagesSearchingProgressBar.visibility = View.VISIBLE
    }

    private fun handleBackButtonPressed() {
        flickrImagesRecyclerView.visibility = View.GONE
        flickrImagesRepository.clearData()
        flickrTextView.visibility = View.VISIBLE
        flickrImageView.visibility = View.VISIBLE
        supportActionBar?.title = resources.getString(R.string.app_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)
    }

    override fun onImageClicked(image: Image) {
        val intent = Intent(this, FlickrImageDetailsActivity::class.java).apply {
            putExtra(Constants.IMAGE_DETAILS_KEY, image)
        }
        startActivity(intent)
    }

}
