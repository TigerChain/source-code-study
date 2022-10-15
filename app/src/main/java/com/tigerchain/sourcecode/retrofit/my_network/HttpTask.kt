package com.tigerchain.sourcecode.retrofit.my_network

import com.alibaba.fastjson.JSON

/**
 * @Description
 */
class HttpTask<T>(
    url: String,
    requestData: T,
    private val httpRequest: IHttpRequest,
    listener: CallbackListener
) :Runnable{

    init {
        httpRequest.setUrl(url)
        httpRequest.setListener(listener)
        val content = JSON.toJSONString(requestData)
        httpRequest.setData(content.toByteArray())
    }

    override fun run() {
        httpRequest.execute()
    }
}

