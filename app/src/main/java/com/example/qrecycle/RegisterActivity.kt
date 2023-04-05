package com.example.qrecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val signInTv : TextView = findViewById(R.id.signInTv)
        val registerBtn : Button = findViewById(R.id.registerBtn)

        registerBtn.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            //finish()
        }

        signInTv.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            //finish()
        }

    }
}