package com.example.qrecycle

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Suppress("DEPRECATION")
class ProfileActivity : AppCompatActivity() {

    private lateinit var profileNameTv   : TextView
    private lateinit var profileEmailTv  : TextView
    private lateinit var profilePointsTv : TextView
    private lateinit var editProfileTv   : TextView
    private lateinit var questsTv        : TextView
    private lateinit var leaderboardTv   : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_profile)

        profileNameTv   = findViewById(R.id.profileFullName)
        profileEmailTv  = findViewById(R.id.profileEmail)
        profilePointsTv = findViewById(R.id.profilePoints)
        editProfileTv   = findViewById(R.id.editYourProfile)
        questsTv        = findViewById(R.id.questsTv)
        leaderboardTv   = findViewById(R.id.leaderboardTv)

        val currentUserEmail = FirebaseAuth.getInstance().currentUser?.email
        val usersRef         = FirebaseDatabase.getInstance().getReference("users")

        usersRef.orderByChild("email").equalTo(currentUserEmail)
            .addListenerForSingleValueEvent(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (userSnapshot in snapshot.children) {
                            val user = userSnapshot.getValue(User::class.java)
                            if (user != null) {
                                profileNameTv.text = getString(R.string.profile_name, user.name)
                                profileEmailTv.text = getString(R.string.profile_email, user.email)
                                profilePointsTv.text =
                                    getString(R.string.profile_points, user.points.toString())
                            }
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(
                        this@ProfileActivity,
                        "Error retrieving user data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        editProfileTv.setOnClickListener{
            editProfile()
        }

        questsTv.setOnClickListener{
            startActivity(Intent(this@ProfileActivity, QuestsActivity::class.java))
        }

        leaderboardTv.setOnClickListener{
            startActivity(Intent(this@ProfileActivity, LeaderboardActivity::class.java))
        }

    }

    private fun editProfile() {
        //TODO: create function editProfile
    }
}
