package com.task.imageuploadsoft.api

import com.task.imageuploadsoft.util.Resource
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface Image {
    suspend fun uploadImage(file: MultipartBody.Part, preset:RequestBody): Resource<Any>

}
