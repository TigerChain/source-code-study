package com.tigerchain.sourcecode.cache.lru

import com.tigerchain.sourcecode.cache.ICache

class LRUCache<K,V : Any>(
    private val capacity:Int
) : ICache<K,V> {

    private inner class Node {
        var key: K? = null
        var value: V? = null
        var next: Node? = null
        var prev: Node? = null
    }

    private val  cache:MutableMap<K,Node>
    private val  head:Node = Node()
    private val  end:Node = Node()


    init {

        head.next = end
        end.prev = head

        cache = HashMap(capacity)

    }



    override fun get(key: K): V? {
        val node: Node = cache[key] ?: return null
        modeNodeToHead(node)
        return node.value

    }

    private fun modeNodeToHead(node: Node) {
        removeNode(node)
        addNodeToHead(node)
    }

    private fun removeNode(node: Node) {

        val prevNode:Node? = node.prev
        val nextNode:Node? = node.next

        prevNode?.next = nextNode
        nextNode?.prev = prevNode

        node.prev = null
        node.next = null

    }

    // 第一个节点添加至头节点
    private fun addNodeToHead(node: Node) {

        node.next = head.next
        head.next?.prev = node

        head.next = node
        node.prev = head

    }

    override fun put(key: K, value: V) {
        var node:Node? = cache[key]

        if(node == null) {
            if(cache.size == capacity) {
                val oldNode: Node = removeEndNode()
                cache.remove(oldNode.key)
            }
            node = this.Node()
            node.key = key
            node.value = value

            cache[key] = node
            addNodeToHead(node)
        }else {
            node.value = value
            modeNodeToHead(node)

        }
    }

    private fun removeEndNode():Node{
        val delNode = end.prev
        removeNode(delNode!!)
        return delNode
    }
}