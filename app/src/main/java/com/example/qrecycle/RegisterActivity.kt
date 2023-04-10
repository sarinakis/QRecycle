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


class RegisterActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var usersRef: DatabaseReference

    //TODO: Add Google, Facebook and Instagram log in options
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_register)

        FirebaseApp.initializeApp(this)
        database = FirebaseDatabase.getInstance()
        usersRef = database.reference.child("users")

        val registerBtn : Button= findViewById(R.id.registerBtn)

        val fullNameInput        : TextInputLayout = findViewById(R.id.editTextFullName)
        val emailInput           : TextInputLayout = findViewById(R.id.editTextEmail)
        val passwordInput        : TextInputLayout = findViewById(R.id.editTextPassword)
        val confirmPasswordInput : TextInputLayout = findViewById(R.id.editTextConfirmPassword)

        registerBtn.setOnClickListener {
            registerUser(fullNameInput, emailInput, passwordInput, confirmPasswordInput)
        }
    }

    private fun registerUser(
        name: TextInputLayout,
        email : TextInputLayout,
        password : TextInputLayout,
        confirmPassword : TextInputLayout
    ){
        val nameStr            = name.editText?.text.toString()
        val emailStr           = email.editText?.text.toString().trim {it <= ' '}
        val passwordStr        = password.editText?.text.toString().trim {it <= ' '}
        val confirmPasswordStr = confirmPassword.editText?.text.toString().trim {it <= ' '}

        if (validateForm(nameStr, emailStr, passwordStr, confirmPasswordStr)) {
            val ref = FirebaseDatabase.getInstance().getReference("users")
            ref.orderByChild("email").equalTo(emailStr).addListenerForSingleValueEvent(
                object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        startLogInActivity()
                        Toast.makeText(
                            this@RegisterActivity,
                            "Email already registered",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    } else {
                        val id = ref.push().key
                        val user = User(id, nameStr, emailStr, passwordStr)

                        if (id != null) {
                            ref.child(id)
                                .setValue(user)
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "You have successfully registered to QRecycle",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    startDashboardActivity()
                                    finish()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        "Registration failed",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Error checking email",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
    }

    private fun startDashboardActivity() {
        val intent = Intent(this, DashBoardActivity::class.java)
        startActivity(intent)
    }

    private fun startLogInActivity() {
        val intent = Intent(this, LogInActivity::class.java)
        startActivity(intent)
    }

    private fun validateForm(name : String, email : String, password : String, confirmPassword : String) : Boolean{
        return when {
            TextUtils.isEmpty(name) -> {
                Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show()
                false
            }
            TextUtils.isEmpty(email) -> {
                Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show()
                false
            }
            TextUtils.isEmpty(password) -> {
                Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
                false
            }
            password != confirmPassword -> {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                false
            }
            else -> true
        }
    }
}