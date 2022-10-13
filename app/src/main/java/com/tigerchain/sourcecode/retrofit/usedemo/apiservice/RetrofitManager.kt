package com.tigerchain.sourcecode.retrofit.usedemo.apiservice

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {

    private var retrofit: Retrofit? = null

    private fun getRetrofit(): Retrofit? {

        if(retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }


    fun <T> create(t:Class<T>): T? {

        return getRetrofit()?.create(t)
    }

}