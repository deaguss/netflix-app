package com.example.netflix_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast


import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var signIn: Button
    private lateinit var signUp: TextView
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()


    private fun initComponents(){
        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        signIn = findViewById(R.id.button_sign_in)
        signUp = findViewById(R.id.sign_up_text)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()

        signUp.setOnClickListener {
            val intent = Intent(this@MainActivity, RegisterPage::class.java)
            startActivity(intent)
            finish()
        }

        signIn.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if(TextUtils.isEmpty(email)){
                Toast.makeText(this@MainActivity, "Enter your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener;
            }

            if(TextUtils.isEmpty(password)){
                Toast.makeText(this@MainActivity, "Enter your password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener;
            }


            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@MainActivity, "Login successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@MainActivity, ChoiceMenu::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        val error = task.exception?.message ?: "Unknown error"
                        Toast.makeText(this@MainActivity, "Authentication failed: $error", Toast.LENGTH_SHORT).show()
                    }

                }

        }
    }
}