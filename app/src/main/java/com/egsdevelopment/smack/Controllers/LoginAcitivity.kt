package com.egsdevelopment.smack.Controllers

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.egsdevelopment.smack.R
import com.egsdevelopment.smack.Services.AuthService
import kotlinx.android.synthetic.main.activity_login_acitivity.*

class LoginAcitivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_acitivity)
    }

    fun loginLoginBtnClicked(view: View) {
        val email = loginEmailText.text.toString()
        val password = loginPasswordText.text.toString()

        AuthService.loginUser(this, email, password) { loginSucces ->
            println("DIE DINGETJE")
            println("DEM TOKEN" + AuthService.authToken)
            if (loginSucces) {
                println("CALLING FINDUSERBYEMAIL")
                AuthService.findUserByEmail(this) { findSuccess ->
                        if (findSuccess) {
                            finish()
                        }
                    }
                }
            }
        }

    fun loginCreateUserBtnClicked(view: View) {
        val createUserIntent = Intent(this, CreateUserActivity::class.java)
        startActivity(createUserIntent)
        finish()
    }
}
