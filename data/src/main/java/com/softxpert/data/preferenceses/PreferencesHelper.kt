package com.softxpert.data.preferenceses

import android.content.Context

class PreferencesHelper(context: Context) {
    private val preferences = context.getSharedPreferences("softxpert_pet_finder", Context.MODE_PRIVATE)

    val authToken: String?
        get() = preferences!!.getString("auth_token", "")

    fun putAuthToken(authToken: String?) {
        preferences!!.edit().putString("auth_token", authToken).apply()
    }


    val isLoggedIn: Boolean
        get() = authToken != ""


}