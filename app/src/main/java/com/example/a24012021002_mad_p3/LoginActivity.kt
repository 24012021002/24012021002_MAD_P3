package com.example.a24012021002_mad_p3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a24012021002_mad_p3.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<EditText>(R.id.et_login_email)
        val etPassword = findViewById<EditText>(R.id.et_login_password)
        val btnLogin = findViewById<Button>(R.id.btn_login_submit)

        btnLogin.setOnClickListener {
            val password = etPassword.text.toString()
            Toast.makeText(this, "Password:$password", Toast.LENGTH_SHORT).show()
        }
    }
}
