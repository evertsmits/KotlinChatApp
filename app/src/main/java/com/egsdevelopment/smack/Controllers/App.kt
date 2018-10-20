package com.egsdevelopment.smack.Controllers

import android.app.Application
import com.egsdevelopment.smack.Utilities.SharedPrefs
import java.util.prefs.AbstractPreferences

class App : Application() {

    companion object {
        lateinit var sharedPreferences: SharedPrefs
    }

    override fun onCreate() {
        sharedPreferences = SharedPrefs(applicationContext)
        super.onCreate()

    }

}