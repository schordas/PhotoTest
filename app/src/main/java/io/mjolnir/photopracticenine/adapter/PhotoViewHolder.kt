package io.mjolnir.photopracticenine.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.mjolnir.photopracticenine.entities.PhotoUrl
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoViewHolder(
    itemView : View,
    private val mListener: PhotoListener
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private val picasso by lazy {
        Picasso.get()
    }

    fun bindTo(photo : PhotoUrl) {
        val image = itemView.photo_view

        itemView.setOnClickListener(this)

        picasso
            .load(photo.url)
            .fit()
            .into(image)

    }
    override fun onClick(v: View?) {
        mListener.onClick(itemView, adapterPosition)
    }

}