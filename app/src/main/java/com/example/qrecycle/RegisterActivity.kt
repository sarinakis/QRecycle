package com.example.qrecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class RegisterActivity : AppCompatActivity() {

    private lateinit var emailText: EditText
    private lateinit var email: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerBtn : Button = findViewById(R.id.registerBtn)
        val emailAddress : TextView = findViewById(R.id.etEmail)

        emailText = emailAddress as EditText
        email = emailText.text.toString().trim()

        registerBtn.setOnClickListener {
            val intent = Intent(this, DashBoardActivity::class.java)
            startActivity(intent)
            //checkEmailAddress(emailAddress, intent)
            //finish()
        }

    }

    private fun checkEmailAddress(view: View, intent: Intent) {
        val text1 = emailText.text

        if(text1.isEmpty()) {
            Toast.makeText(this,"Please enter your email address",Toast.LENGTH_SHORT).show()
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(text1).matches()) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            }
        }
    }
}