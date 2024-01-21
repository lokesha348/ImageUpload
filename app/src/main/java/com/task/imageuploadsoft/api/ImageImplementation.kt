package com.task.imageuploadsoft.api

import android.util.Log
import com.task.imageuploadsoft.util.Resource
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ImageImplementation(val imageAPI: ImageAPI) : Image {
    override suspend fun uploadImage(file: MultipartBody.Part, preset: RequestBody): Resource<Any> {

        return try {

            val response = imageAPI.uploadImage(file, preset)


            val result = response.body()
            Log.d("XXXXXXXXX", "" + result.toString())

            if (response.isSuccessful) {
                Log.d("XXXXXXXXX", "" + result.toString())

                Resource.Success(result!!)


            } else {
                Resource.Error(response.message())
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "An error occured")
        }
    }

}