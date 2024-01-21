package com.task.imageuploadsoft.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInterface {


    companion object {

        var instance: Retrofit? = null
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        fun getRetrofitInstance(): Retrofit {

            if (instance == null) {

                instance = Retrofit.Builder()
                    .baseUrl("https://api.cloudinary.com/v1_1/dcypzty0c/").client(client)
                    .addConverterFactory(GsonConverterFactory.create()).build()

            }

            return instance!!


        }

    }
}
