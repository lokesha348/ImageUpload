package com.task.imageuploadsoft.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.imageuploadsoft.api.Image
import com.task.imageuploadsoft.api.ImageAPI
import com.task.imageuploadsoft.api.ImageImplementation
import com.task.imageuploadsoft.retrofit.RetrofitInterface
import com.task.imageuploadsoft.util.Resource
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream

class MainActivityViewModel : ViewModel() {


    private val imageFile_ = MutableLiveData<File>()
    val imageFile: LiveData<File>
        get() = imageFile_


    private val mainEvent_ = MutableLiveData<MainEvent>()
    val mainEvent: LiveData<MainEvent>
        get() = mainEvent_


    var image: Image? = null
    var imageApi: ImageAPI? = null


    init {

        imageApi = RetrofitInterface.getRetrofitInstance().create(ImageAPI::class.java)
        image = ImageImplementation(imageApi!!)

    }


    sealed class MainEvent {

        class Success(val jsonObject: Any) : MainEvent()
        class Failure(val m: String) : MainEvent()
        object Empty : MainEvent()
        object Loading : MainEvent()

    }


    fun uploadImage(file: File) {
        mainEvent_.value = MainEvent.Loading

        val profileImage: RequestBody = RequestBody.create(
            "image/jpg".toMediaTypeOrNull(),
            file
        )

        val profileImageBody: MultipartBody.Part =
            MultipartBody.Part.createFormData(
                "file",
                file.getName(), profileImage
            )

        val preset = getMultiPartFormRequestBody("maozn8ci")

        viewModelScope.launch {
            val response = image?.uploadImage(profileImageBody, preset)
            when (response) {
                is Resource.Success -> {
                    mainEvent_.value = MainEvent.Success(response.data!!)
                }
                is Resource.Error -> {
                    mainEvent_.value = MainEvent.Failure(response.message!!)
                }
                else -> {}
            }
        }
    }

    fun getMultiPartFormRequestBody(tag: String?): RequestBody {
        return RequestBody.create(MultipartBody.FORM, tag!!)
    }

    fun setImageFile(file: File) {
        imageFile_.value = file
    }

    fun compressImage(file: File) {
        try {
            val bitmap = BitmapFactory.decodeFile(file.path)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, FileOutputStream(file))
            Log.d("Success", "image compressed")
        } catch (t: Throwable) {
            Log.e("ERROR", "Error compressing file.$t")
            t.printStackTrace()
        }
    }

}