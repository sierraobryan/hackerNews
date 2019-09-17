package com.example.myapplication.di

import android.app.Activity
import android.app.Application
import android.os.Bundle
import timber.log.Timber

class MainApplicationInitializer(
    application: Application
)
    : BaseApplicationInitializer(application) {

    override fun initialize() {
        super.initialize()
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                //RiseAndShine.riseAndShine(activity)
            }

            override fun onActivityStarted(activity: Activity) {

            }

            override fun onActivityResumed(activity: Activity) {

            }

            override fun onActivityPaused(activity: Activity) {

            }

            override fun onActivityStopped(activity: Activity) {

            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

            }

            override fun onActivityDestroyed(activity: Activity) {

            }
        })
    }
}