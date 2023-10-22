package com.shady.data.preferenceses

import android.content.Context

class PreferencesHelper(context: Context) {
    private val preferences = context.getSharedPreferences("boyot", Context.MODE_PRIVATE)

    val authToken: String? get() = preferences.getString("auth_token", null)

    fun putAuthToken(authToken: String?) {
        preferences.edit().putString("auth_token", authToken).apply()
    }

    fun clear() {
        preferences.edit().clear().apply()
    }
    val isLoggedIn: Boolean
        get() = authToken != null


}