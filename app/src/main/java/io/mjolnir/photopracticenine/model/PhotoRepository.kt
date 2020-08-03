package io.mjolnir.photopracticenine.model

import android.util.Log
import io.mjolnir.photopracticenine.entities.Photo
import io.mjolnir.photopracticenine.entities.PhotoUrl
import io.mjolnir.photopracticenine.network.PhotoService

class PhotoRepository(private val photoDao: PhotoDao) {

    private val photoService by lazy {
        PhotoService.create()
    }

    val photos = photoDao.getPhotos()

    suspend fun insert(photos : List<Photo.Results>) {
        for (photo in photos) {
            photoDao.insert(PhotoUrl(photo.urls.regular))
        }
    }

    suspend fun insert(photo: Photo.Results) {
        photoDao.insert(PhotoUrl(photo.urls.regular))
    }

    suspend fun fetchPhotos() {
        val call = photoService.getPhotos(1).execute()

        if (call.isSuccessful) {
            val photos = call.body()
            if (photos != null) {
                insert(photos)
            }
        } else {
            Log.d(PhotoRepository::class.java.simpleName, call.message())
            Log.d(PhotoRepository::class.java.simpleName, call.errorBody().toString())
        }
    }


    suspend fun fetchPhoto() {
        val call = photoService.getPhoto().execute()

        if (call.isSuccessful) {
            val photo = call.body()
            if (photo != null) {
                insert(photo)
            }
        } else {
            Log.d(PhotoRepository::class.java.simpleName, call.message())
            Log.d(PhotoRepository::class.java.simpleName, call.errorBody().toString())

        }
    }
}
