package io.mjolnir.photopracticenine.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import io.mjolnir.photopracticenine.model.PhotoDatabase
import io.mjolnir.photopracticenine.model.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhotoWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        return@withContext try {
            val dao = PhotoDatabase
                .getDatabase(applicationContext, this)
                .photoDao()

            PhotoRepository(dao).fetchPhoto()

            Result.success()
        } catch (error: Throwable) {
            Result.failure()
        }
    }

}