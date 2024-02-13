package com.sks.firebase

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.sks.firebase.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    private lateinit var signupBinding: ActivitySignupBinding

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        signupBinding = ActivitySignupBinding.inflate(layoutInflater)
        val view = signupBinding.root
        setContentView(view)

        supportActionBar?.title = "Sign Up"

        signupBinding.buttonSignupUser.setOnClickListener {
            val userEmail = signupBinding.editTextEmailSignup.text.toString()
            val userPassword = signupBinding.editTextPasswordSignup.text.toString()
            signupWithFirebase(userEmail, userPassword)
        }
    }

    private fun signupWithFirebase(userEmail: String, userPassword: String) {
        auth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    applicationContext,
                    "Your account has been created",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                Toast.makeText(applicationContext, task.exception?.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
}