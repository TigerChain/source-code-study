package com.tigerchain.sourcecode.retrofit.my_network
import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * @Description
 */
class ThreadPoolManager{

    private var mQueue:LinkedBlockingQueue<Runnable> = LinkedBlockingQueue()
    private var mThreadPoolExecutor:ThreadPoolExecutor? = null

    init {
        mThreadPoolExecutor = ThreadPoolExecutor(3,10,15,TimeUnit.SECONDS,
        ArrayBlockingQueue<Runnable>(5)
        ) { r, _ -> addTask(r) }

        mThreadPoolExecutor?.execute(coreThread())
    }


    companion object {
        val instance by lazy(LazyThreadSafetyMode.NONE) {
            ThreadPoolManager()
        }
    }


    fun addTask(runnable: Runnable) {
        mQueue.add(runnable)
    }


    /**
     * 核心线程
     */
    private fun coreThread():Runnable = Runnable {
        while (true){
            // 从队列中取出一个任务
            val run = mQueue.take()
            // 交给线程池去执行
            mThreadPoolExecutor?.execute(run)
        }
    }



}