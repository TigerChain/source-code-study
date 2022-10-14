package com.tigerchain.sourcecode.rxjava2.myrxjava

// 定义观察者
interface Observer<T> {

    fun onCompleted()

    fun onError(throwable: Throwable?)

    fun onNext(value: T)
}