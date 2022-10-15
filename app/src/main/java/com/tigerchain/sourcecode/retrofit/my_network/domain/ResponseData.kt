package com.tigerchain.sourcecode.retrofit.my_network.domain
import com.alibaba.fastjson.annotation.JSONCreator

data class ApiResponseData @JSONCreator constructor (
    val data:MutableList<Banner>,
    val errorCode: Int,
    val errorMsg:String
)

data class Banner @JSONCreator constructor(
    val desc:String,
    val id:Int,
    val imagePath:String,
    val url:String,
    val title:String,
    val order:Int,
    val type:Int
)