package com.example.myapplication.di

import android.app.Application
import android.content.Intent

import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.security.ProviderInstaller
import com.google.android.gms.security.ProviderInstaller.ProviderInstallListener

abstract class BaseApplicationInitializer(
    protected val application: Application) {

    open fun initialize() {

 //       Timber.plant(logger)

        upgradeSecurityProvider()
    }

    private fun upgradeSecurityProvider() {
        ProviderInstaller.installIfNeededAsync(application, object : ProviderInstallListener {
            override fun onProviderInstalled() {

            }

            override fun onProviderInstallFailed(errorCode: Int, recoveryIntent: Intent) {
                GoogleApiAvailability.getInstance().showErrorNotification(application, errorCode)
            }
        })
    }
}