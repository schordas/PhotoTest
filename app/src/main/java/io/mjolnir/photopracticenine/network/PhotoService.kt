package io.mjolnir.photopracticenine.network

import io.mjolnir.photopracticenine.BASE_URL
import io.mjolnir.photopracticenine.entities.Photo
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotoService {

    @GET("/photos")
    fun getPhotos(@Query("page") page : Int) : Call<List<Photo.Results>>

    @GET("/photos/random")
    fun getPhoto() : Call<Photo.Results>

    companion object {
        fun create() : PhotoService {
            val client = OkHttpClient.Builder()
                .addInterceptor(HeaderInterceptor())
                .build()
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(PhotoService::class.java)
        }
    }
}