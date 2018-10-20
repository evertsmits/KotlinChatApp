package com.egsdevelopment.smack.Controllers

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.egsdevelopment.smack.R
import com.egsdevelopment.smack.Services.AuthService
import com.egsdevelopment.smack.Services.UserDataService
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
    }

    fun generateUserAvatar(view: View) {
        val random = Random()
        val color = random.nextInt(2) //generate number between 0 till 1
        val avatar = random.nextInt(28) //generate number between 0 till 27

        if (color == 0){
            userAvatar = "light$avatar"
        } else {
            userAvatar = "dark$avatar"
        }

        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName) //get image resource id from drawables using string name

        createAvatarImgView.setImageResource(resourceId)
    }

    fun createUserClicked(view: View) {
        val email = createEmailText.text.toString()
        val password = createPasswordText.text.toString()
        val userName = createUserNameText.text.toString()
        AuthService.registerUser(this, email, password){registerSucces ->
            if (registerSucces){
                AuthService.loginUser(this, email, password){loginSucces ->
                    if (loginSucces) {
                        AuthService.createUser(this, userName, email, userAvatar, avatarColor) {createSuccess ->
                            if (createSuccess) {
                                println(UserDataService.avatarName)
                                println(UserDataService.avatarColor)
                                println(UserDataService.name)
                                finish()
                            }
                        }
                    }
                }
            }

        }
    }

    fun generateColorClicked(view: View) {
        val random = Random()
        val r = random.nextInt(255) // R value for RGB
        val g = random.nextInt(255) // G value for RGB
        val b = random.nextInt(255) // B value for RGB

        createAvatarImgView.setBackgroundColor(Color.rgb(r,g,b))

        val savedR = r.toDouble() / 255
        val savedG = g.toDouble() / 255
        val savedB = b.toDouble() / 255

        avatarColor = "[${savedR}, ${savedG}, ${savedB}, 1]"
    }
}