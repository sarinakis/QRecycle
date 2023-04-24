package com.example.qrecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
class SignActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_sign)

        val sinInBtn = findViewById<Button>(R.id.signInBtn)
        val sinUpBtn = findViewById<Button>(R.id.signUpBtn)

        sinInBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        sinUpBtn.setOnClickListener {
            startActivity(Intent(this, LogInActivity::class.java))
        }

    }

}