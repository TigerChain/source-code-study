package com.tigerchain.sourcecode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tigerchain.sourcecode.databinding.ActivityMainBinding
import com.tigerchain.sourcecode.retrofit.usedemo.apiservice.ApiService
import com.tigerchain.sourcecode.retrofit.usedemo.apiservice.RetrofitManager
import com.tigerchain.sourcecode.retrofit.usedemo.domain.Banner
import com.tigerchain.sourcecode.retrofit.usedemo.domain.ResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {


    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        initView()
    }

    private fun initView() {

        mainBinding.clickBtn.setOnClickListener {
            val apiService = RetrofitManager.create(ApiService::class.java)
            apiService?.let {
                val call:Call<ResponseData<List<Banner>>> = it.getBanner()
                call.enqueue(object :Callback<ResponseData<List<Banner>>>{
                    override fun onResponse(
                        call: Call<ResponseData<List<Banner>>>,
                        response: Response<ResponseData<List<Banner>>>
                    ) {
                        println(response.body().toString())
                    }

                    override fun onFailure(call: Call<ResponseData<List<Banner>>>, t: Throwable) {
                    }

                })
            }
        }
    }
}

