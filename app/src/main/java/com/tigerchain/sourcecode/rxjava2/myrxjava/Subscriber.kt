package com.tigerchain.sourcecode.rxjava2.myrxjava

abstract class Subscriber<T> : Observer<T> {
    fun onStart() {}
}