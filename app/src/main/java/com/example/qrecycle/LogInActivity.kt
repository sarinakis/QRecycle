package com.example.qrecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*

class LogInActivity : AppCompatActivity() {

    private lateinit var database : FirebaseDatabase
    private lateinit var usersRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("DEPRECATION")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_log_in)

        FirebaseApp.initializeApp(this)
        database = FirebaseDatabase.getInstance()
        usersRef = database.reference.child("users")

        val loginBtn           = findViewById<Button>(R.id.loginBtn)
        val emailInput         = findViewById<TextInputLayout>(R.id.editTextEmail)
        val passwordInput      = findViewById<TextInputLayout>(R.id.editTextPassword)

        val intent = intent
        val email = intent.getStringExtra("email")

        if (!email.isNullOrEmpty()) {
            emailInput.editText?.setText(email)
        }

        loginBtn.setOnClickListener {
            val emailStr    = emailInput.editText?.text.toString().trim { it <= ' ' }
            val passwordStr = passwordInput.editText?.text.toString().trim { it <= ' ' }

            if (validateForm(emailStr, passwordStr)) {
                usersRef.orderByChild("email")
                    .equalTo(emailStr)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            for (userSnapshot in snapshot.children) {
                                val user = userSnapshot.getValue(User::class.java)
                                if (user != null && user.password == passwordStr) {
                                    startDashboardActivity()
                                    finish()
                                    return
                                }
                            }
                        }
                        Toast.makeText(
                            this@LogInActivity,
                            "Invalid email or password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(
                            this@LogInActivity,
                            "Login failed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        }
    }

    private fun startDashboardActivity() {
        startActivity(Intent(this, DashBoardActivity::class.java))
    }

    private fun validateForm(email: String, password: String): Boolean {
        return when {
            TextUtils.isEmpty(email) -> {
                Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show()
                false
            }
            TextUtils.isEmpty(password) -> {
                Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }
}