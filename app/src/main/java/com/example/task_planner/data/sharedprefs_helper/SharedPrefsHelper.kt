package com.example.task_planner.data.sharedprefs_helper

import android.content.Context
import com.example.task_planner.common.SharedPrefsConstants

class SharedPrefsHelper(context: Context) {
    private val sharedPrefs = context.getSharedPreferences(
        SharedPrefsConstants.APP_NAME_SHAREDPREFS, Context.MODE_PRIVATE
    )

    fun setToken(token: String) {
        if(token.isNotBlank()) sharedPrefs.edit().putString(SharedPrefsConstants.JSON_TOKEN, token).apply()
    }
    fun getToken(): String? = sharedPrefs.getString(SharedPrefsConstants.JSON_TOKEN, "")
    fun containsTokens(): Boolean = sharedPrefs.contains(SharedPrefsConstants.JSON_TOKEN)

    companion object {
        private var INSTANCE: SharedPrefsHelper? = null

        fun getInstance(context: Context): SharedPrefsHelper {
            if (INSTANCE == null) {
                INSTANCE = SharedPrefsHelper(context)
            }
            return INSTANCE!!
        }
    }
}