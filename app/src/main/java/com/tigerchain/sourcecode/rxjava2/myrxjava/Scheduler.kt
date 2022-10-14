package com.tigerchain.sourcecode.rxjava2.myrxjava

import java.util.concurrent.Executor
import java.util.concurrent.Executors

class Scheduler(private val executor:Executor) {

    companion object {
        private val ioScheduler = Scheduler(Executors.newSingleThreadExecutor())

        fun io(): Scheduler {
            return ioScheduler
        }
    }

    fun createWorker():Worker = Worker(executor)

    inner class Worker(w_executor: Executor) {
        private var w_executor: Executor

        init {
            this.w_executor = w_executor
        }

        fun schedule(runnable: Runnable?) {
            w_executor.execute(runnable)
        }
    }


}