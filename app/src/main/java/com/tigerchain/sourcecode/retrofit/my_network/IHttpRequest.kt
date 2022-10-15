package com.tigerchain.sourcecode.retrofit.my_network

/**
 * @Description
 */
interface IHttpRequest {

    fun setUrl(url:String)

    fun setData(data:ByteArray)

    fun setListener(listener: CallbackListener)

    fun execute()
}