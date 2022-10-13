package com.tigerchain.sourcecode.eventbus.myeventbus

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executors

class MyEventBus private constructor(){

    private var cacheMap: MutableMap<Any, List<SubscribeMethod>>? = null
    private var handler:Handler?= null

    init {
        cacheMap = mutableMapOf()
        handler = Handler(Looper.getMainLooper())
    }

    // 双检锁单例
    companion object {
        private val instance:MyEventBus by  lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED){
            MyEventBus()
        }

        fun getDefault():MyEventBus  = instance
    }



    fun register(obj:Any) {
        var list : List<SubscribeMethod>? = cacheMap?.get(obj)
        if(list == null){
            list = findSubscribeMethods(obj)
            cacheMap?.put(obj, list)
        }
    }

    private fun findSubscribeMethods(obj: Any?): List<SubscribeMethod> {

        val list = mutableListOf<SubscribeMethod>()

        var clazz = obj?.javaClass

        while (clazz!=null){
            val name = clazz.name
            if(name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("android.") || name.startsWith("androidx.")) {
                break
            }

            val declaredMethods = clazz.declaredMethods
            for(method in declaredMethods){
                val subscribe = method.getAnnotation(Subscribe::class.java) ?: continue
                val types = method.parameterTypes
                if(types.size !=1) {
                    println("1111")
                }

                val threadMode = subscribe.threadMode
                val subscribeMethod = SubscribeMethod(method,threadMode,types[0])
                list.add(subscribeMethod)

            }
            clazz = clazz.superclass
        }

        return  list
    }


    fun post(type:Any) {
        val keys = cacheMap?.keys

        cacheMap?.forEach { (k,v)->
            run {
                val list = cacheMap?.get(k)
                list?.let {
                    interatorSubscribeMethods(list, k, type)
                }

            }
        }
    }

    private fun interatorSubscribeMethods(
        list: List<SubscribeMethod>,
        k: Any,
        type: Any
    ) {
        for (subscribeMethod in list) {
            when (subscribeMethod.threadMode) {
                ThreadMode.MAIN_THREAD -> {
                    if (Looper.myLooper() == Looper.getMainLooper()) { // 如果在线程发送事件，则直接在主线程响应
                        invoke(subscribeMethod, k, type)
                    } else {
                        handler?.post { //如果在线程送事件，则要把事件发送到主线程响应
                            invoke(subscribeMethod, k, type)
                        }
                    }
                }
                ThreadMode.SUB_THREAD -> {
                    if (Looper.myLooper() != Looper.getMainLooper()) {
                        invoke(subscribeMethod, k, type)
                    } else {
                        val executeService = Executors.newFixedThreadPool(5)
                        executeService.execute {
                            invoke(subscribeMethod, k, type)
                        }

                    }
                }
            }
        }
    }

    private fun invoke(subscribeMethod: SubscribeMethod, obj: Any, type: Any) {
        try {
            val method = subscribeMethod.method
            method.invoke(obj, type)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

}