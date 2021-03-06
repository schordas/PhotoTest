package io.mjolnir.photopracticenine.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import io.mjolnir.photopracticenine.entities.PhotoUrl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [PhotoUrl::class], version = 1, exportSchema = false)
abstract class PhotoDatabase : RoomDatabase() {

    abstract fun photoDao(): PhotoDao

    companion object {

        @Volatile
        var INSTANCE : PhotoDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ) : PhotoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    PhotoDatabase::class.java,
                    "url_database"
                )
                    .addCallback(PhotoDatabaseCallback(scope))
                    .build()

                INSTANCE = instance

                instance
            }
        }

        private class PhotoDatabaseCallback(
            private val scope: CoroutineScope
        ) : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { photoDatabase ->
                    scope.launch(Dispatchers.IO) {
                        populateDb(photoDatabase.photoDao())
                    }
                }

            }
        }

        private suspend fun populateDb(dao: PhotoDao) {
            PhotoRepository(dao).fetchPhotos()
        }
    }
}