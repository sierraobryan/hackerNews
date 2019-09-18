package com.example.myapplication.di

import android.content.Context
import android.text.TextUtils
import com.example.myapplication.R
import com.example.myapplication.util.putOrClearPreference

open class Settings(private val context: Context) {

    private val preferences = context.getSharedPreferences(PREFS_SETTINGS, Context.MODE_PRIVATE)

    var baseUrl: String
        get() = preferences.getString(PREF_BASE_URL, context.getString(R.string.default_base_url))!!
        set(baseUrl) = preferences.putOrClearPreference(
            PREF_BASE_URL,
            !TextUtils.isEmpty(baseUrl),
            baseUrl
        )

    companion object {
        private const val PREFS_SETTINGS = "settings"  // NON-NLS

        private const val PREF_BASE_URL = "base_url"  // NON-NLS
    }
}