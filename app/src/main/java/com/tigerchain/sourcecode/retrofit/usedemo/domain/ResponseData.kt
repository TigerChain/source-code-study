package com.tigerchain.sourcecode.retrofit.usedemo.domain

data class ResponseData<T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String
)