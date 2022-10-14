package com.tigerchain.sourcecode.rxjava2.myrxjava


/***
 * 定义被观察者
 */
class Observable<T>(private val onSubscribe: OnSubscribe<T>?) {

    companion object {
        fun <T> create(onSubscribe: OnSubscribe<T>?): Observable<T> {
            return Observable(onSubscribe)
        }
    }

    fun subscribe(subscriber: Subscriber<T>) {
        subscriber.onStart()
        onSubscribe?.call(subscriber)
    }

    interface OnSubscribe<T> {
        fun call(subscriber: Subscriber<in T>)
    }

    fun <R> map(transformer : Fun1<T, R>): Observable<R> {

        return create(object : OnSubscribe<R>{
            override fun call(subscriber: Subscriber<in R>) {
               onSubscribe?.call(subscriberImpl(subscriber, transformer))
            }
        })
    }

    private fun <R> subscriberImpl(
        subscriber: Subscriber<in R>,
        transformer: Fun1<T, R>
    ) = object : Subscriber<T>() {
        override fun onCompleted() {
            subscriber.onCompleted()
        }

        override fun onError(throwable: Throwable?) {
            subscriber.onError(throwable)
        }

        override fun onNext(value: T) {
            subscriber.onNext(transformer.transfer(value))
        }

    }

    interface Fun1<T, R> {
        fun transfer(from: T): R
    }


    fun observeOn(scheduler: Scheduler): Observable<T> {
        return create(object :OnSubscribe<T>{
            override fun call(subscriber: Subscriber<in T>) {
                subscriber.onStart()
                val worker = scheduler.createWorker()
                onSubscribe?.call(subscriberObserveOnIO(worker, subscriber))
            }
        })
    }

    private fun subscriberObserveOnIO(
        worker: Scheduler.Worker,
        subscriber: Subscriber<in T>
    ) = object : Subscriber<T>() {
        override fun onCompleted() {
        }

        override fun onError(throwable: Throwable?) {
        }

        override fun onNext(value: T) {
            worker.schedule {
                subscriber.onNext(value)
            }
        }

    }

    fun subscribeOn(scheduler: Scheduler):Observable<T> {
        return create(object :OnSubscribe<T>{
            override fun call(subscriber: Subscriber<in T>) {
                scheduler.createWorker().schedule{
                    onSubscribe?.call(subscriber)
                }
            }
        })
    }
}