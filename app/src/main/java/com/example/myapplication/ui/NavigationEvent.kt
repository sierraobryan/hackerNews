package com.example.myapplication.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class NavigationEvent<T> : SingleLiveEvent<T>() {
    fun observe(owner: LifecycleOwner, observer: NavigationObserver<T>) {
        super.observe(owner, Observer { event ->
            if (event == null) {
                return@Observer
            }
            observer.onNavigationEvent(event)
        })
    }

    interface NavigationObserver<in T> {
        /**
         * Called when there is a new navigation event.
         */
        fun onNavigationEvent(event: T)
    }
}