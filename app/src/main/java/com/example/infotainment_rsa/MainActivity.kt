package com.example.infotainment_rsa

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.infotainment_rsa.components.MapBox
import com.example.infotainment_rsa.components.SelectAnIssue
import com.example.infotainment_rsa.components.selectedIssue
import com.example.infotainment_rsa.model.AddressModel
import com.example.infotainment_rsa.ui.theme.InfotainmentRSATheme
import com.example.infotainment_rsa.viewmodel.MainViewModel
import com.google.gson.Gson
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.geometry.LatLng
import kotlinx.coroutines.delay
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.text.DecimalFormat


private lateinit var viewModel: MainViewModel

class MainActivity : ComponentActivity(), OnMapReadyCallback {
    private var currentLocation: Location? = null
    lateinit var locationManager: LocationManager

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
            InfotainmentRSATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = Color.Black
                ) {
                    if (intent.getStringExtra("service") != null) {
                        viewModel.intentIssue = intent.getStringExtra("service").toString()
                    } else {

                    }

                    if (!viewModel.intentIssue.isEmpty() && !viewModel.intenissueAdded) {
                        viewModel.listOfIssues.add(viewModel.intentIssue)
                        viewModel.listOfIssuesImage.add(R.drawable.accident)
                        viewModel.selectedIndex = viewModel.listOfIssues.size - 1
                        viewModel.intenissueAdded = true
                    }

                    val context = LocalContext.current
                    val locationGranted = isLocationPermissionGranted(this)
                    locationManager =
                        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    val hasNetwork =
                        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)


                    val gpsLocationListener: LocationListener = object : LocationListener {
                        override fun onLocationChanged(location: Location) {
                            viewModel.lattiude = location.latitude
                            viewModel.longitutde = location.longitude
                        }

                        override fun onStatusChanged(
                            provider: String, status: Int, extras: Bundle
                        ) {
                        }

                        override fun onProviderEnabled(provider: String) {}
                        override fun onProviderDisabled(provider: String) {

                        }
                    }

                    val networkLocationListener: LocationListener = object : LocationListener {
                        override fun onLocationChanged(location: Location) {
                            val precision = DecimalFormat("0.0000")

                            viewModel.lattiude = precision.format(location.latitude).toDouble()
                            viewModel.longitutde = precision.format(location.longitude).toDouble()
                        }

                        override fun onStatusChanged(
                            provider: String, status: Int, extras: Bundle
                        ) {
                        }

                        override fun onProviderEnabled(provider: String) {}
                        override fun onProviderDisabled(provider: String) {}
                    }

                    if (hasGps) {
                        if (ActivityCompat.checkSelfPermission(
                                this, Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                this, Manifest.permission.ACCESS_COARSE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            return@Surface
                        }
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, 5000, 0F, gpsLocationListener
                        )
                    }

                    if (hasNetwork) {
                        locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER, 5000, 0F, networkLocationListener
                        )
                    }

                    val lastKnownLocationByGps =
                        locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    lastKnownLocationByGps?.let { location ->
                        val precision = DecimalFormat("0.0000")

                        viewModel.lattiude = precision.format(location.latitude).toDouble()
                        viewModel.longitutde = precision.format(location.longitude).toDouble()
                    }

                    LaunchedEffect(key1 = viewModel.executed) {
                        if (viewModel.executed) {
                            while (viewModel.processIndex <= 3 && viewModel.processStart) {
                                delay(3000)
                                viewModel.listOfProcessExcuted.add(viewModel.processIndex)
                                viewModel.processIndex++;
                                if (viewModel.processIndex == 4) {
                                    viewModel.processIndex = 3
                                    viewModel.processStart = false
                                }
                            }
                        }
                    }


                    var mainBackGroundGradient = Brush.linearGradient(
                        listOf(
                            Color(0xFF040A2F),
                            Color(0xFF060817)
                        )
                    )

                    var backgroundGradient = Brush.linearGradient(
                        listOf(
                            Color(0xFF040F36),
                            Color(0xFF030A29)
                        )
                    )

                    Box(modifier = Modifier.fillMaxSize().background(brush = mainBackGroundGradient)){
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(brush = backgroundGradient)
                        ) {
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = "RSA Emergency Assistance",
                                style = TextStyle(
                                    color = Color.White,
                                    fontFamily = FontFamily(Font(R.font.manrope_extrabold))
                                )
                            )
                            Spacer(modifier = Modifier.size(10.dp))

                            if (viewModel.selectedIndex != -1) {
                                Row(
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Spacer(modifier = Modifier.size(5.dp))
                                    MapBox(
                                        Modifier.weight(2f), this@MainActivity,
                                        viewModel
                                    )
                                    Spacer(modifier = Modifier.size(5.dp))
                                    selectedIssue(
                                        modifier = Modifier.weight(1f),
                                        viewModel
                                    )
                                }
                            } else {
                                SelectAnIssue(modifier = Modifier.fillMaxSize(), viewModel) {
                                    viewModel.selectedIndex = it
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        val latLong: LatLng = LatLng(viewModel.lattiude, viewModel.longitutde)
        val markerOptions: MarkerOptions = MarkerOptions().position(latLong)
        var markerAdded by mutableStateOf(false)
        markerOptions.title = viewModel.areaName
        markerOptions.snippet = viewModel.address
        val markers = mapplsMap.markers
        if (markers.size > 0) {
            for (i in 0 until markers.size) {
                if (markers[i].title == viewModel.areaName) {
                    markerAdded = true
                }
            }
        }
        if (!markerAdded && viewModel.areaName.isNotEmpty()) {
            mapplsMap?.addMarker(markerOptions)
        }


        val latLongTechnician: LatLng = LatLng(viewModel.lattiude - 0.0005, viewModel.longitutde)
        val markerOptionsTechnician: MarkerOptions = MarkerOptions().position(latLongTechnician)
        var TechnicianmarkerAdded by mutableStateOf(false)
        if (viewModel.processIndex >= 2) {
            if (markers.size > 0) {
                for (i in 0 until markers.size) {
                    if (markers[i].title == "Technician") {
                        TechnicianmarkerAdded = true
                    }
                }
            }
            if (!TechnicianmarkerAdded) {
                markerOptionsTechnician.title = "Technician"
                markerOptionsTechnician.snippet = "Technician address"
                mapplsMap?.addMarker(markerOptionsTechnician)!!
            }
        } else {
            if (markers.size > 0) {
                for (i in 0 until markers.size) {
                    if (markers[i].title == "Technician") {
                        mapplsMap?.removeMarker(markers[i])
                    }
                }
            }
        }
        val cameraPosition = CameraPosition.Builder().target(
            LatLng(
                viewModel.lattiude, viewModel.longitutde
            )
        ).zoom(15.0).tilt(0.0).build()
        mapplsMap.cameraPosition = cameraPosition

        // get Location Address
        val precision = DecimalFormat("0.0000")

        val lattitudeFromated = precision.format(viewModel.lattiude).toDouble()
        val longitudeFromated = precision.format(viewModel.longitutde).toDouble()
        if (viewModel.areaName.isEmpty()) {
            getlocationAddress(lattitudeFromated, longitudeFromated)
        }
    }

    private fun getlocationAddress(lattiude: Double, longitutde: Double) {
        Log.d("Sudarshan API CALL ", "call APi")
        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
            .url("https://apis.mappls.com/advancedmaps/v1/4a0bbbe5ab800eca852f98bc67c7313b/rev_geocode?lat=${12.8946721}&lng=${77.60990}")
            .build()

        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val gson = Gson()
                if (response.code == 200) {
                    val addressResponse = gson.fromJson(responseBody, AddressModel::class.java)
                    if (addressResponse != null) {
                        viewModel.areaName = addressResponse.results[0].locality
                        viewModel.address = addressResponse.results[0].formatted_address
                    } else {
                        viewModel.areaName = "Area Name"
                        viewModel.address = "No Response"
                    }
                } else {
                    viewModel.areaName = "Area Name"
                    viewModel.address = "No Response"
                }
            }
        })
    }

    override fun onMapError(p0: Int, p1: String?) {
    }
}

private fun isLocationPermissionGranted(mainActivity: MainActivity): Boolean {
    return if (ActivityCompat.checkSelfPermission(
            mainActivity, android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            mainActivity, android.Manifest.permission.ACCESS_FINE_LOCATION
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        ActivityCompat.requestPermissions(
            mainActivity, arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ), 100
        )
        false
    } else {
        true
    }
}


