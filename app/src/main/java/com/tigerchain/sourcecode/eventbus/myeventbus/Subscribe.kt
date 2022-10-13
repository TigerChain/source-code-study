package com.tigerchain.sourcecode.eventbus.myeventbus



@Target(AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Subscribe(val threadMode:ThreadMode = ThreadMode.MAIN_THREAD)