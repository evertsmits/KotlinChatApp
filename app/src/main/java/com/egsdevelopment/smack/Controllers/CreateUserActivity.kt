package com.egsdevelopment.smack.Controllers

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.view.View
import android.widget.Toast
import com.egsdevelopment.smack.R
import com.egsdevelopment.smack.Services.AuthService
import com.egsdevelopment.smack.Services.UserDataService
import com.egsdevelopment.smack.Utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
        createSpinner.visibility = View.INVISIBLE
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
        enableSpinner(true)
        val email = createEmailText.text.toString()
        val password = createPasswordText.text.toString()
        val userName = createUserNameText.text.toString()

        if (userName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()){
            AuthService.registerUser(this, email, password){registerSucces ->
                if (registerSucces){
                    AuthService.loginUser(this, email, password){loginSucces ->
                        if (loginSucces) {
                            AuthService.createUser(this, userName, email, userAvatar, avatarColor) {createSuccess ->
                                if (createSuccess) {
                                    val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                                    enableSpinner(false)
                                    finish()
                                } else {
                                    errorToast()
                                }
                            }
                        } else {
                            errorToast()
                        }
                    }
                } else {
                    errorToast()
                }

            }
        } else {
           fillInCredentialsToast()
        }


    }

    fun errorToast() {
        enableSpinner(false)
        Toast.makeText(this, "Something went wrong, please try again", Toast.LENGTH_LONG).show()
    }

    fun fillInCredentialsToast() {
        enableSpinner(false)
        Toast.makeText(this, "Please fill in all the credentials", Toast.LENGTH_SHORT).show()
    }

    fun enableSpinner(enable: Boolean) {
        if (enable) {
            createSpinner.visibility = View.VISIBLE
        } else {
            createSpinner.visibility = View.INVISIBLE
        }
        createUserBtn.isEnabled = !enable
        createAvatarImgView.isEnabled = !enable
        createBackgroundColorBtn.isEnabled = !enable
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
