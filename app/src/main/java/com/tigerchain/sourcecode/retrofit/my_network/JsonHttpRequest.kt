package com.tigerchain.sourcecode.retrofit.my_network

import java.io.BufferedOutputStream
import java.io.InputStream
import java.lang.RuntimeException
import java.net.HttpURLConnection
import java.net.URL

/**
 * @Description
 */
class JsonHttpRequest : IHttpRequest {

    private lateinit var url:String
    private lateinit var data:ByteArray
    private lateinit var callbackListener: CallbackListener

    override fun setUrl(url: String) {
        this.url = url
    }

    override fun setData(data: ByteArray) {
        this.data = data

    }

    override fun setListener(callbackListener: CallbackListener) {
        this.callbackListener = callbackListener
    }

    private lateinit var httpURLConnection : HttpURLConnection

    override fun execute() {


        try {
            val url:URL = URL(this.url)
            httpURLConnection = url.openConnection() as HttpURLConnection
            httpURLConnection.connectTimeout = 6000
            httpURLConnection.useCaches = false
            httpURLConnection.instanceFollowRedirects = true
            httpURLConnection.readTimeout = 3000
            httpURLConnection.doInput = true
            httpURLConnection.doOutput = true
            httpURLConnection.requestMethod = "GET"
            httpURLConnection.setRequestProperty("Content-Type","application/json;charset=UTF-8")
            httpURLConnection.connect()

            val outputStream = httpURLConnection.outputStream
            val bos:BufferedOutputStream = BufferedOutputStream(outputStream)
            bos.write(data)
            bos.flush()
            outputStream.close()
            bos.close()

            if(httpURLConnection.responseCode == HttpURLConnection.HTTP_OK){
                val inputStream:InputStream = httpURLConnection.inputStream
                callbackListener.onSuccess(inputStream)
            }else {
                throw  RuntimeException("请求失败")
            }
        }catch(e:Exception){
            e.printStackTrace()
            throw  RuntimeException("请求失败")
        }finally {
            httpURLConnection.disconnect()

        }
    }
}