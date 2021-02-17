package com.hanamin.weather.utils.extensions

import androidx.lifecycle.MutableLiveData


fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }
