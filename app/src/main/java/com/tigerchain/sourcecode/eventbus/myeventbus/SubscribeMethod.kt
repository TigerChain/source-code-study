package com.tigerchain.sourcecode.eventbus.myeventbus

import java.lang.reflect.Method

class SubscribeMethod(
    val method:Method,
    val threadMode: ThreadMode,
    val type:Class<*>
)