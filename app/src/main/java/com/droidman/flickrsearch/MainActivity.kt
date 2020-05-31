package com.droidman.flickrsearch

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.droidman.flickrsearch.api.Photo
import com.droidman.flickrsearch.recyclerview.PhotosAdapter
import com.droidman.flickrsearch.repository.PhotosRepository
import com.droidman.flickrsearch.utils.Constants
import com.droidman.flickrsearch.utils.Utility

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var searchView : SearchView
    private lateinit var photosRepository : PhotosRepository
    private lateinit var searchingProgressBar : ProgressBar
    private lateinit var searchingTextView : TextView
    private lateinit var flickrLogoTextView : TextView
    private lateinit var flickrLogoImageView : ImageView
    private lateinit var photosListView : RecyclerView

    private var searchString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        photosListView = findViewById(R.id.photos_list_view)
        searchingProgressBar = findViewById(R.id.searching_progress_bar)
        searchingTextView = findViewById(R.id.searching_text_view)
        flickrLogoTextView = findViewById(R.id.flickr_header_text_view)
        flickrLogoImageView = findViewById(R.id.flickr_logo_image_view)
        photosRepository = PhotosRepository()
        val photosAdapter = PhotosAdapter()
        photosListView.layoutManager = GridLayoutManager(this, if (Utility.isLandscape(this)) Constants.IMAGES_PER_ROW_IN_LANDSCAPE else Constants.IMAGES_PER_ROW_IN_PORTRAIT)
        photosListView.adapter = photosAdapter

        val photosObserver = Observer<List<Photo>> { photos ->
            photosAdapter.submitList(photos)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.title = "results for $searchString"
            searchingTextView.visibility = View.GONE
            searchingProgressBar.visibility = View.GONE
            photosListView.visibility = View.VISIBLE
        }
        photosRepository.photosList.observe(this, photosObserver)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        val searchItem = menu.findItem(R.id.search)
        searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            flickrLogoTextView.visibility = View.VISIBLE
            flickrLogoImageView.visibility = View.VISIBLE
            photosListView.visibility = View.GONE
            photosRepository.clearData()
            supportActionBar?.title = resources.getString(R.string.app_name)
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setDisplayShowHomeEnabled(false)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        photosRepository.getPhotos(query)
        searchingTextView.visibility = View.VISIBLE
        searchingProgressBar.visibility = View.VISIBLE
        photosListView.visibility = View.GONE
        flickrLogoImageView.visibility = View.GONE
        flickrLogoTextView.visibility = View.GONE
        searchingTextView.text = "looking for $query"
        searchString = query
        searchView.setQuery("", false)
        searchView.isIconified = true
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        return false
    }

}
