package com.shady.data.preferenceses

import android.content.Context
import android.util.Log

class PreferencesHelper(context: Context) {
    private val preferences = context.getSharedPreferences("boyot", Context.MODE_PRIVATE)

    fun getAuthToken(): String? {
        return preferences.getString("auth_token", null)
    }
    fun putAuthToken(authToken: String?) {
        preferences.edit().putString("auth_token", authToken).apply()
    }

    fun clear() {
        Log.d("TAG", "clear: "+getAuthToken())
        preferences.edit().remove("auth_token").apply()
    }

    fun isLoggedIn(): Boolean {
        Log.d("TAG", "isLoggedIn: "+getAuthToken())
        Log.d("TAG", "isLoggedIn: "+(!getAuthToken().isNullOrEmpty()).toString())
        return !getAuthToken().isNullOrEmpty()
    }


}