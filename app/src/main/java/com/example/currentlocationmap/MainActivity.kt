package com.example.currentlocationmap

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.currentlocationmap.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var currentLocation: Location? = null

    // Permission launcher
    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) ||
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                // Permission granted, get location
                getCurrentLocation()
            }
            else -> {
                // Permission denied
                Toast.makeText(this, "Location permission is required to show your current location", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize location services
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Initialize map
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Set up refresh button
        binding.fabRefreshLocation.setOnClickListener {
            if (checkLocationPermission()) {
                getCurrentLocation()
            } else {
                requestLocationPermission()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        
        // Enable zoom controls
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isMyLocationButtonEnabled = true

        // Check and request location permission
        if (checkLocationPermission()) {
            getCurrentLocation()
        } else {
            requestLocationPermission()
        }
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
        ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    private fun getCurrentLocation() {
        if (checkLocationPermission()) {
            try {
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location: Location? ->
                        location?.let {
                            currentLocation = it
                            val latLng = LatLng(it.latitude, it.longitude)
                            
                            // Clear previous markers
                            mMap.clear()
                            
                            // Add marker for current location
                            mMap.addMarker(
                                MarkerOptions()
                                    .position(latLng)
                                    .title("Current Location")
                                    .snippet("Lat: ${it.latitude}, Lng: ${it.longitude}")
                            )
                            
                            // Move camera to current location
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                            
                            // Update location info
                            updateLocationInfo(it)
                        } ?: run {
                            Toast.makeText(this, "Unable to get current location", Toast.LENGTH_SHORT).show()
                        }
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error getting location: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            } catch (e: SecurityException) {
                Toast.makeText(this, "Location permission not granted", Toast.LENGTH_SHORT).show()
            }
        } else {
            requestLocationPermission()
        }
    }

    private fun updateLocationInfo(location: Location) {
        binding.tvLocationInfo.text = """
            Current Location:
            Latitude: ${location.latitude}
            Longitude: ${location.longitude}
            Accuracy: ${location.accuracy} meters
        """.trimIndent()
    }
} 