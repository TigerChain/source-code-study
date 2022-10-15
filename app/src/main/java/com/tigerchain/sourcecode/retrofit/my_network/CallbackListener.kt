package com.tigerchain.sourcecode.retrofit.my_network

import java.io.InputStream

/**
 * @Description
 */
interface CallbackListener {

    fun onSuccess(inputStream: InputStream)
    fun onFailure()
}