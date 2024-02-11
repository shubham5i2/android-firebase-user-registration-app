package com.sks.firebase

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.sks.firebase.databinding.ActivityUpdateUserBinding

class UpdateUserActivity : AppCompatActivity() {

    private lateinit var updateUserBinding: ActivityUpdateUserBinding

    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = database.reference.child("MyUsers")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateUserBinding = ActivityUpdateUserBinding.inflate(layoutInflater)
        val view = updateUserBinding.root
        setContentView(view)

        supportActionBar?.title = "Update User"

        getAndSetData()

        updateUserBinding.buttonUpdateUser.setOnClickListener {
            updateData()
        }
    }

    private fun getAndSetData() {
        val name = intent.getStringExtra("name")
        val age = intent.getIntExtra("age", 0).toString()
        val email = intent.getStringExtra("email")

        updateUserBinding.editTextUpdateName.setText(name)
        updateUserBinding.editTextUpdateAge.setText(age)
        updateUserBinding.editTextUpdateEmail.setText(email)
    }

    private fun updateData() {
        val updatedName = updateUserBinding.editTextUpdateName.text.toString()
        val updatedAge = updateUserBinding.editTextUpdateAge.text.toString().toInt()
        val updatedEmail = updateUserBinding.editTextUpdateEmail.text.toString()
        val userId = intent.getStringExtra("id").toString()

        val userMap = mutableMapOf<String, Any>()
        userMap["userId"] = userId
        userMap["userName"] = updatedName
        userMap["userAge"] = updatedAge
        userMap["userEmail"] = updatedEmail

        databaseReference.child(userId).updateChildren(userMap).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(
                    applicationContext, "User has been updated", Toast.LENGTH_LONG
                ).show()
                finish()
            } else {
                Toast.makeText(
                    applicationContext, task.exception.toString(), Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}