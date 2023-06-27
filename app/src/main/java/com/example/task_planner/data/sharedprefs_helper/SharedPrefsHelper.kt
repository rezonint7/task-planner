package com.example.task_planner.data.sharedprefs_helper

import android.content.Context
import com.example.task_planner.common.SharedPrefsConstants

class SharedPrefsHelper(context: Context) {
    private val sharedPrefs = context.getSharedPreferences(
        SharedPrefsConstants.APP_NAME_SHAREDPREFS, Context.MODE_PRIVATE
    )

    fun setToken(tokens: Tokens) {
        if(tokens.access_token != null) sharedPrefs.edit().putString(SharedPrefsConstants.JSON_TOKENS, Gson().toJson(tokens)).apply()
    }

    fun getToken(): Tokens? {
        val json = sharedPrefs.getString(SharedPrefsConstants.JSON_TOKENS, "")
        return Gson().fromJson(json, Tokens::class.java) ?: null
    }

    fun containsTokens(): Boolean {
        return sharedPrefs.contains(SharedPrefsConstants.JSON_TOKENS)
    }

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