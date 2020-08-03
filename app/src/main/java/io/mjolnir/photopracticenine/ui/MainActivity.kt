package io.mjolnir.photopracticenine.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import io.mjolnir.photopracticenine.R
import io.mjolnir.photopracticenine.adapter.PhotoAdapter
import io.mjolnir.photopracticenine.adapter.PhotoListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: PhotoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val recyclerView = recycler_view

        recyclerView.layoutManager = GridLayoutManager(
            this,
            2)

        adapter = PhotoAdapter(object : PhotoListener {
            override fun onClick(view: View, position: Int) {
                val photo =  adapter.getPhoto(position)

                Toast
                    .makeText(this@MainActivity, photo.url, Toast.LENGTH_LONG)
                    .show()
            }
        })


        recyclerView.adapter = adapter

        val viewModel = ViewModelProvider(this)
            .get(PhotoViewModel::class.java)

        viewModel.photos.observe(this, Observer {
            adapter.updatePhotos(it)
        })

        //viewModel.startWorker()
    }
}
