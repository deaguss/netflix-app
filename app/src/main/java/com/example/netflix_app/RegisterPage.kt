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
        signUp = findViewById(R.id.button_sign_in)
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
            val email: String = editTextEmail.text.toString()
            val password: String = editTextPassword.text.toString()

            if(TextUtils.isEmpty(email)){
                Toast.makeText(this@RegisterPage, "Enter create your email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener;
            }

            if(TextUtils.isEmpty(password)){
                Toast.makeText(this@RegisterPage, "Enter create your password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener;
            }


            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@RegisterPage, "Created an account successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterPage, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@RegisterPage, "Authentication failed", Toast.LENGTH_SHORT).show()
                    }

                }

        }

    }
}