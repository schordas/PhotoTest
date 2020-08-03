package io.mjolnir.photopracticenine.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "url_table")
class PhotoUrl(@PrimaryKey @ColumnInfo(name = "url") val url: String)
