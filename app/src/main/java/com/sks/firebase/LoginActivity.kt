package com.sks.firebase

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.sks.firebase.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = loginBinding.root
        setContentView(view)

        supportActionBar?.title = "Sign In"

        loginBinding.buttonSignin.setOnClickListener {
            val userEmail = loginBinding.editTextEmailSignin.text.toString()
            val userPassword = loginBinding.editTextPasswordSignin.text.toString()
            signinWithFirebase(userEmail, userPassword)
        }

        loginBinding.buttonSignup.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }

        loginBinding.buttonForgotPassword.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgetActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signinWithFirebase(userEmail: String, userPassword: String) {
        auth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, open homepage of the app
                    Toast.makeText(
                        applicationContext, "Login is successful", Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Sign in fails
                    Toast.makeText(
                        applicationContext, task.exception?.toString(), Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    // onStart() is overridden to check if user is already logged in or not
    override fun onStart() {
        super.onStart()

        val user = auth.currentUser

        if (user != null) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}