package com.tigerchain.sourcecode.retrofit.my_network
/**
 * @Description
 */
class MyHttp {

    companion object {
        fun <T,M> sendJsonRequest(
            url:String,
            requestData:T,
            response:Class<M>,
            listener: IJsonDataTransformListener<M>
        ) {

            val httpRequest:IHttpRequest = JsonHttpRequest()
            val callBackListener = JsonCallBackListener(response, listener)

            val httpTask = HttpTask(
                url,
                requestData,
                httpRequest,
                callBackListener
            )

            ThreadPoolManager.instance.addTask(httpTask)

        }
    }
}