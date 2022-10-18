package com.tigerchain.sourcecode.cache.fifo

import com.tigerchain.sourcecode.cache.ICache
import java.util.ArrayDeque
import java.util.Queue

class FIFOCache<K,V>(
    private val capacity:Int
) : ICache<K,V> {

    private val  cache:MutableMap<K,V>
    private val  queue:Queue<K>

    init {
        cache = HashMap(capacity)
        queue = ArrayDeque(capacity)
    }

    override fun get(key: K): V? {
        return cache[key]
    }

    override fun put(key: K, value: V) {

        val oldValue = cache[key]

        if(oldValue==null){
            if(cache.size == capacity) {
                val oldKey = queue.poll()
                oldKey?.let {
                    cache.remove(oldKey)
                }
            }
            queue.offer(key) // 把 key 存入到队列尾
        }
        cache[key] = value
    }


}

fun main() {
    val cache: FIFOCache<Int,Int> = FIFOCache(5)
    cache.put(1,1)
    cache.put(2,2)
    cache.put(3,3)
    cache.put(4,4)
    cache.put(5,5)
    cache.put(6,6)

    println(cache.toString())

}