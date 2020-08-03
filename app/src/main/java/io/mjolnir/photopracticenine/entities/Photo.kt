package io.mjolnir.photopracticenine.entities

object Photo {
    data class Results(val urls: URLs)
    data class URLs(val regular : String)
}