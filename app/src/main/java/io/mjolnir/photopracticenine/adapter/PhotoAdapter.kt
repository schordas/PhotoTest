package io.mjolnir.photopracticenine.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.mjolnir.photopracticenine.R
import io.mjolnir.photopracticenine.entities.PhotoUrl

class PhotoAdapter(
    private val mListener: PhotoListener
) : RecyclerView.Adapter<PhotoViewHolder>() {

    private var photos = emptyList<PhotoUrl>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_photo,
                parent,
                false
            )
        return PhotoViewHolder(view, mListener)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bindTo(photos[position])
    }


    fun updatePhotos(photos: List<PhotoUrl>) {
        this.photos = photos
        notifyDataSetChanged()
    }

    fun getPhoto(position: Int) : PhotoUrl {
        return photos[position]
    }

}