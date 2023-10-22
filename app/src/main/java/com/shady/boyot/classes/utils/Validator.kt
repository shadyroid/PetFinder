package com.shady.boyot.classes.utils

import android.content.Context
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import com.shady.boyot.ui.MainActivity
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

class Validator @Inject constructor(@ActivityContext val context: Context,val appToast: AppToast) {
    private val emailPattern = Patterns.EMAIL_ADDRESS.pattern()
    private val phonePattern = Patterns.PHONE.pattern()


    fun isNotEmpty(textView: TextView, errorResource: Int): Boolean {
        val value = textView.text.toString().trim { it <= ' ' }
        return isNotEmpty(value, errorResource)
    }

    fun isNotEmpty(value: String?, errorResource: Int): Boolean {
        if (value == null || value == "") {
            appToast.showMessage(context.getString(errorResource))
            return false
        }
        return true
    }

    fun isEmail(textView: TextView, errorResource: Int): Boolean {
        val value = textView.text.toString().trim { it <= ' ' }
        if (!value.matches(Regex(emailPattern))) {
            appToast.showMessage(context.getString(errorResource))
            return false
        }
        return true
    }

    fun isMoreThan(textView: TextView, limit: Int, errorResource: Int): Boolean {
        val value = textView.text.toString().trim { it <= ' ' }
        if (value.length <= limit) {
            appToast.showMessage(context.getString(errorResource))
            return false
        }
        return true
    }

    fun isLessThan(textView: TextView, limit: Int, errorResource: Int): Boolean {
        val value = textView.text.toString().trim { it <= ' ' }
        if (value.length >= limit) {
            appToast.showMessage(context.getString(errorResource))
            return false
        }
        return true
    }

    fun isEqualTo(textView: TextView, limit: Int, errorResource: Int): Boolean {
        val value = textView.text.toString().trim { it <= ' ' }
        if (value.length != limit) {
            appToast.showMessage(context.getString(errorResource))
            return false
        }
        return true
    }

    fun isMatch(firstTextView: TextView, secondTextView: TextView, errorResource: Int): Boolean {
        val firstValue = firstTextView.text.toString().trim { it <= ' ' }
        val secondValue = secondTextView.text.toString().trim { it <= ' ' }
        if (firstValue != secondValue) {
            appToast.showMessage(context.getString(errorResource))
            return false
        }
        return true
    }
}