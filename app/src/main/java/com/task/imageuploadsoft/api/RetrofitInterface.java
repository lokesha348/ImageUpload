package com.task.imageuploadsoft.api;

import com.task.imageuploadsoft.model.MyResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface RetrofitInterface {
    String BASE_URL = "http://192.168.43.124/ImageUploadApi/";

    @Multipart
    @POST("Api.php?apicall=upload")
    Call<MyResponse> uploadOpPhoto(RequestBody requestFile, RequestBody descBody);
}
