package com.task.demo.configuration

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceManager
import com.task.demo.utils.JsonUtils

class AppPreferences(var context: Context) {
    lateinit var instance: AppPreferences

    companion object {
        private var USER_JSON = "USER_JSON"
        private var USER_TOKEN = "USER_TOKEN"
        private var USER_LNG = "USER_LNG"
        private var USER_FONT = "USER_FONT"
        private var USER_THEME = "USER_THEME"
    }

    protected var sharedPreferences: SharedPreferences

    init {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun init(context: Context): AppPreferences {
        return if (::instance.isInitialized) {
            instance
        } else {
            instance = AppPreferences(context)
            instance
        }
    }

//    fun setUserProfile(value: UserDto) {
//        setString(USER_JSON, JsonUtils.convertPojoToString(value))
//    }
//
//    fun getUserProfile(): UserDto {
//        val value = sharedPreferences.getString(USER_JSON, null)
//        return JsonUtils.convertStringToPojo(value, UserDto::class.java) as UserDto
//    }

    fun setUserToken(token: String) {
        setString(USER_TOKEN, token)
    }

    fun getUserToken(): String? {
        return getString(USER_TOKEN)
    }

    fun setUserLng(lng: String) {
        setString(USER_LNG, lng)
    }

    fun getUserLng(): String {
        return if (getString(USER_LNG).isNullOrBlank()) "en"
        else getString(USER_LNG)!!
    }

    fun setUserFont(lng: Float) {
        setFloat(USER_FONT, lng)
    }

    fun getUserFont(): Float {
        return if (getFloat(USER_FONT) != 0F) getFloat(USER_FONT)
        else 1.0F
    }

    fun setTheme(lng: Int) {
        setString(USER_THEME, lng.toString())
    }

    fun getTheme(): Int {
        return if (getString(USER_THEME) != null) getString(USER_THEME)!!.toInt()
        else AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    private fun setString(key: String, value: String) {
        val editor: Editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    private fun setFloat(key: String, value: Float) {
        val editor: Editor = sharedPreferences.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    private fun setInt(key: String, value: Int) {
        val editor: Editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    private fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    private fun getFloat(key: String): Float {
        return sharedPreferences.getFloat(key, 0F)
    }

    private fun getString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    private fun getBoolean(key: String?): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    private fun setBoolean(key: String?, value: Boolean) {
        val editor: Editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun clearAll() {
        val editor: Editor = sharedPreferences.edit()
        editor.apply()
        editor.clear()
        editor.apply()
    }

    fun resetConfig() {
        val editor: Editor = sharedPreferences.edit()
        editor.remove(USER_FONT)
        editor.remove(USER_LNG)
        editor.remove(USER_THEME)
        editor.apply()
    }
}