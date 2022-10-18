package com.tigerchain.sourcecode.cache

interface ICache<K,V> {

    fun get(key:K) : V ?
    fun put(key: K,value:V)
}