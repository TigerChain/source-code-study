
package com.tigerchain.sourcecode.retrofit.my_network

import IJsonDataTransformListener
import com.tigerchain.sourcecode.retrofit.my_network.domain.ApiResponseData

fun main() {
    sendRequest()
}
private val url:String = "https://www.wanandroid.com/banner/json"

private fun sendRequest() {

    MyHttp.sendJsonRequest(
        url,
        null,
        ApiResponseData::class.java,
        object :IJsonDataTransformListener<ApiResponseData>{
            override fun onSuccess(t: ApiResponseData) {
//                Log.e("111",t.data.toString())
                println(t.toString())
            }

        }
    )
}