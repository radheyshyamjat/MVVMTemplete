package com.task.demo.utils

import android.app.Activity
import android.content.res.Configuration
import android.util.Log
import android.util.Patterns
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.task.demo.BuildConfig
import com.task.demo.configuration.App
import okhttp3.ResponseBody

fun toastShort(message: String?) {
    if (!message.isNullOrBlank()) Toast.makeText(App.getInstance(), message, Toast.LENGTH_SHORT)
        .show()
}

fun toastLong(message: String?) {
    if (!message.isNullOrBlank()) Toast.makeText(App.getInstance(), message, Toast.LENGTH_LONG)
        .show()
}

fun showErrorMessage(response: ResponseBody?) {
    try {
        val jsonString: JsonElement = JsonParser().parse(response!!.charStream())
        val message: String = jsonString.asJsonObject["message"].asString
        val code: Int = jsonString.asJsonObject["code"].asInt
        toastShort(message)
    } catch (e: Exception) {

    }
}

fun debug(logMessage: String) {
    if (BuildConfig.DEBUG) {
        Log.d("Assignment", logMessage)
    }
}

fun info(infoMessage: String) {
    if (BuildConfig.DEBUG) {
        Log.i("Assignment", infoMessage)
    }
}

fun errorDebug(errorMessage: String) {
    if (BuildConfig.DEBUG) {
        Log.e("Assignment", errorMessage)
    }
}

fun checkEmail(email: String): Boolean {
    return if (email.trim().isNullOrBlank()) false
    else {
        val pattern = Patterns.EMAIL_ADDRESS
        pattern.matcher(email).matches()
    }
}

fun isValidPassword(password: String?): Boolean {
    password?.let {
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"
        val passwordMatcher = Regex(passwordPattern)

        return passwordMatcher.find(password) != null
    } ?: return false
}

fun adjustFontScale(activity: Activity, configuration: Configuration) {
    configuration.fontScale = App.getPreferences().getUserFont()
    val metrics = activity.resources.displayMetrics
    val wm = activity.getSystemService(AppCompatActivity.WINDOW_SERVICE) as WindowManager
    wm.defaultDisplay.getMetrics(metrics)
    metrics.scaledDensity = configuration.fontScale * metrics.density
    activity.baseContext.resources.updateConfiguration(configuration, metrics)
}
