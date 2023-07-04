@file:Suppress("DEPRECATION")

package com.example.qrecycle

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

class DashBoardActivity : AppCompatActivity() {

    private val LOCATION_PERMISSION_REQUEST_CODE = 123
    private val INITIAL_ZOOM_LEVEL = 17.0
    private val INITIAL_LATITUDE = 41.13971119802141
    private val INITIAL_LONGITUDE = 24.887498812792536
    private lateinit var mapView: MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @Suppress("DEPRECATION")
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_dash_board)

        val profileBtn = findViewById<ImageButton>(R.id.profileBtn)

        profileBtn.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        // Initialize the map view
        mapView = findViewById(R.id.mapView)
        mapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        mapView.controller.setZoom(INITIAL_ZOOM_LEVEL)
        mapView.setMultiTouchControls(true)
        mapView.setBuiltInZoomControls(false)

        // Request location permission
        requestLocationPermission()
    }
    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        } else {
            // Permission already granted, center the map on the initial location
            centerMapOnLocation()
        }
    }
    private fun centerMapOnLocation() {
        val initialLocation = GeoPoint(INITIAL_LATITUDE, INITIAL_LONGITUDE)
        mapView.controller.setCenter(initialLocation)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            // Location permission granted, center the map on the initial location
            centerMapOnLocation()
        } else {
            Toast.makeText(
                this@DashBoardActivity,
                "Location permission denied",
                Toast.LENGTH_SHORT
            ).show()
            finish()
        }
    }
}