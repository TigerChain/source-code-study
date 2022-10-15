package com.tigerchain.sourcecode.retrofit.my_network

import android.os.Handler
import android.os.Looper
import com.alibaba.fastjson.JSON
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader

/**
 * @Description
 */
class JsonCallBackListener<T>(private var response: Class<T>,
                              private var listener:IJsonDataTransformListener<T>)
    : CallbackListener{

    private val mHandler by lazy {
        // 主线程 Handler
        Handler(Looper.getMainLooper())
    }

    override fun onSuccess(inputStream: InputStream) {
        // 将流转化成 response 对象
        val content = convertToString(inputStream)
        val parseObject = JSON.parseObject(content, response)
        // 将子线程切换到主线程
        mHandler.post { listener.onSuccess(parseObject) }
    }

    override fun onFailure() {

    }

    private fun convertToString(`is`: InputStream?): String {
        val bReader = BufferedReader(InputStreamReader(`is`))
        val buffer = StringBuffer()
        var line: String? = null
        try {
            while (bReader.readLine().also { line = it } != null) {
                buffer.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                bReader.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return buffer.toString()
    }
}