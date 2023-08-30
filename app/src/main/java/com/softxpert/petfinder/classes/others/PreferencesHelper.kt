package com.softxpert.petfinder.classes.others

import android.content.Context
import android.content.SharedPreferences
import com.softxpert.petfinder.R

object PreferencesHelper {
    private var preferences: SharedPreferences? = null
    fun init(context: Context) {
        preferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    }

    val authToken: String?
        get() = preferences!!.getString("auth_token", "")

    fun putAuthToken(authToken: String?) {
        preferences!!.edit().putString("auth_token", authToken).apply()
    }


    val isLoggedIn: Boolean
        get() = authToken != ""


}