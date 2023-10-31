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

class RegisterPage : AppCompatActivity() {

    private lateinit var editTextEmail: TextInputEditText
    private lateinit var editTextPassword: TextInputEditText
    private lateinit var signUp: Button
    private lateinit var signIn: TextView
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private fun initComponents(){
        editTextEmail = findViewById(R.id.email)
        editTextPassword = findViewById(R.id.password)
        signUp = findViewById(R.id.button_sign_up)
        signIn = findViewById(R.id.sign_in_text)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)

        initComponents()

        signIn.setOnClickListener {
            val intent = Intent(this@RegisterPage, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        signUp.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                // Memastikan email dan kata sandi tidak kosong
                Toast.makeText(this@RegisterPage, "Please enter both email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Autentikasi berhasil
                        Toast.makeText(this@RegisterPage, "Account created successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterPage, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // Autentikasi gagal
                        val error = task.exception?.message ?: "Unknown error"
                        Toast.makeText(this@RegisterPage, "Authentication failed: $error", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}