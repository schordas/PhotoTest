package io.mjolnir.photopracticenine.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import io.mjolnir.photopracticenine.WORKER_ID
import io.mjolnir.photopracticenine.entities.PhotoUrl
import io.mjolnir.photopracticenine.model.PhotoDatabase
import io.mjolnir.photopracticenine.model.PhotoRepository
import io.mjolnir.photopracticenine.worker.PhotoWorker
import java.util.concurrent.TimeUnit

class PhotoViewModel(application: Application) : AndroidViewModel(application) {

    val photos : LiveData<List<PhotoUrl>>

    private val repository: PhotoRepository

    init {
        val dao = PhotoDatabase
            .getDatabase(application.applicationContext, viewModelScope)
            .photoDao()

        repository = PhotoRepository(dao)

        photos = repository.photos
    }


    fun startWorker() {
        val worker = PeriodicWorkRequestBuilder<PhotoWorker>(15, TimeUnit.MINUTES)
            .build()

        WorkManager
            .getInstance(getApplication())
            .enqueueUniquePeriodicWork(WORKER_ID,
                ExistingPeriodicWorkPolicy.KEEP,
                worker)
    }
}