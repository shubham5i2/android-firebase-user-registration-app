package com.sks.firebase

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.sks.firebase.databinding.ActivityForgetBinding

class ForgetActivity : AppCompatActivity() {

    private lateinit var forgetBinding: ActivityForgetBinding

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forgetBinding = ActivityForgetBinding.inflate(layoutInflater)
        val view = forgetBinding.root
        setContentView(view)

        supportActionBar?.title = "Forget Password"

        forgetBinding.buttonReset.setOnClickListener {

            val email = forgetBinding.editTextResetEmail.text.toString()

            auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Password reset email sent to your email address",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
            }
        }
    }
}