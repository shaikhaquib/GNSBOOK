package com.digital.gnsbook.Config;

import com.squareup.okhttp.RequestBody;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RetroClient {
    @Multipart
    @POST("add_product_by_companies")
    Call<ResponseBody>uploadProduct(
            @Part("description") RequestBody description,
            @Part List<MultipartBody.Part> files
    );

}
