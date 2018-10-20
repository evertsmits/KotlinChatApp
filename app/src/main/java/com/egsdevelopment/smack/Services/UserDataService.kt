package com.egsdevelopment.smack.Services

import android.graphics.Color
import com.egsdevelopment.smack.Controllers.App
import java.util.*

object UserDataService {

    var id = ""
    var avatarColor = ""
    var avatarName = ""
    var email = ""
    var name = ""


    fun returnAvatarColor(components: String) : Int{
        val strippedColor = components
            .replace("[", "")
            .replace("]", "")
            .replace(",", "")

        var r = 0
        var g = 0
        var b = 0

        println("Strippedcolor: ${strippedColor}")

        val scanner = Scanner(strippedColor).useLocale(Locale.US)

        if (scanner.hasNext()) {
            r = (scanner.nextDouble() * 255).toInt()
            g = (scanner.nextDouble() * 255).toInt()
            b = (scanner.nextDouble() * 255).toInt()
        }

        return Color.rgb(r,g,b)

    }

    fun logout() {
        id = ""
        avatarColor = ""
        avatarName = ""
        email = ""
        name = ""
        App.sharedPreferences.authToken = ""
        App.sharedPreferences.userEmail = ""
        App.sharedPreferences.isLoggedIn = false
    }

}