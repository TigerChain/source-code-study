package com.tigerchain.sourcecode.retrofit.usedemo.apiservice

import com.tigerchain.sourcecode.retrofit.usedemo.domain.Banner
import com.tigerchain.sourcecode.retrofit.usedemo.domain.ResponseData
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("banner/json")
    fun getBanner(): Call<ResponseData<List<Banner>>>
}