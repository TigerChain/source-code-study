package com.tigerchain.sourcecode.glide

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class GlideUtils {

    companion object {
        private val glideInstance  by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            GlideUtils()
        }

        fun getInstance() = glideInstance
    }

    fun loadImg(context:Context,imgUrl:String,iv:ImageView) {
        Glide.with(context)
            .load(imgUrl)
            .into(iv)
    }

    fun loadCircleImg(context: Context,imgUrl: String,iv: ImageView) {
        Glide.with(context)
            .load(imgUrl)
            .circleCrop()
            .thumbnail(0.1f)
            .into(iv)
    }


    fun loadCircleImg2(context: Context,imgUrl: String,iv: ImageView) {
        Glide.with(context)
            .load(imgUrl)
            .skipMemoryCache(true) // 跳过内存缓存
            .diskCacheStrategy(DiskCacheStrategy.NONE) // 不缓存 sd 卡
            .circleCrop()
            .thumbnail(0.5f) // 缩略图，原图的二分之一
            .into(iv)
    }
}