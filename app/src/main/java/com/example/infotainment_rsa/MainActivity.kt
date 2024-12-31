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
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.example.infotainment_rsa.components.SelectAnIssue
import com.example.infotainment_rsa.model.AddressModel
import com.example.infotainment_rsa.ui.theme.InfotainmentRSATheme
import com.example.infotainment_rsa.viewmodel.MainViewModel
import com.google.gson.Gson
import com.mappls.sdk.maps.MapView
import com.mappls.sdk.maps.Mappls
import com.mappls.sdk.maps.MapplsMap
import com.mappls.sdk.maps.OnMapReadyCallback
import com.mappls.sdk.maps.annotations.Marker
import com.mappls.sdk.maps.annotations.MarkerOptions
import com.mappls.sdk.maps.camera.CameraPosition
import com.mappls.sdk.maps.geometry.LatLng
import com.mappls.sdk.services.account.MapplsAccountManager
import kotlinx.coroutines.delay
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.text.DecimalFormat
import java.util.Calendar

var lattiude by mutableDoubleStateOf(0.00)
var longitutde by mutableDoubleStateOf(0.00)
var selectedIndex by mutableIntStateOf(-1)
var areaName by mutableStateOf("")
var address by mutableStateOf("")
var listOfIssues = mutableStateListOf<String>()
var listOfIssuesImage = mutableStateListOf<Int>()
var executed by mutableStateOf(false)
var mapIsReady by mutableStateOf(false)
var processIndex by mutableStateOf(0)
var listOfProcessExcuted = ArrayList<Int>()
var processStart by mutableStateOf(false)
var listOfServiceTimeKey = mutableStateListOf<String>()
var listOfServiceTimeValue = mutableStateListOf<String>()
var intentIssue by mutableStateOf("")
var intenissueAdded by mutableStateOf(false)

class MainActivity : ComponentActivity(), OnMapReadyCallback {
    private var currentLocation: Location? = null
    lateinit var locationManager: LocationManager

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: MainViewModel by viewModels<MainViewModel>()
            InfotainmentRSATheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    if (intent.getStringExtra("service") != null) {
                        intentIssue = intent.getStringExtra("service").toString()
                    } else {

                    }
                    listOfIssues.clear()
                    listOfIssuesImage.clear()

                    if (!intentIssue.isEmpty()) {
                        listOfIssues.add(intentIssue)
                        listOfIssuesImage.add(R.drawable.accident)
                        selectedIndex = 0
//                        executed = false
//                        processIndex = 0
//                        processStart = false
//                        listOfProcessExcuted.clear()
                        intenissueAdded = true
                    }
                    listOfIssues.add("Accident")
                    listOfIssues.add("Battery Jump Start")
                    listOfIssues.add("Clutch/Break Problem")
                    listOfIssues.add("Lost/ Loacked Keys")
                    listOfIssues.add("Flat Tyre")
                    listOfIssues.add("Fuel Problem")
                    listOfIssues.add("BreakDown")
                    listOfIssues.add("Engine OverHeating")

                    listOfIssuesImage.add(R.drawable.accident)
                    listOfIssuesImage.add(R.drawable.battery)
                    listOfIssuesImage.add(R.drawable.clutch_brake)
                    listOfIssuesImage.add(R.drawable.lost_keys)
                    listOfIssuesImage.add(R.drawable.flat_tyre)
                    listOfIssuesImage.add(R.drawable.fuel_probelm)
                    listOfIssuesImage.add(R.drawable.accident)
                    listOfIssuesImage.add(R.drawable.engine_overheat)


                    listOfServiceTimeKey.clear()
                    listOfServiceTimeKey.add("Hang in there!")
                    listOfServiceTimeKey.add("We've got you!")
                    listOfServiceTimeKey.add("Help is on the way!")
                    listOfServiceTimeKey.add("myTvs to the rescue!")

                    listOfServiceTimeValue.clear()
                    listOfServiceTimeValue.add("Emergency Assist request in being processed on priority.")
                    listOfServiceTimeValue.add("Emergency Assist request is assigned to the nearest available technician.")
                    listOfServiceTimeValue.add("myTVS Technician is on the way to your location and will reach soon!")
                    listOfServiceTimeValue.add("The technician has arrived and will guide you with the next steps")

                    val context = LocalContext.current
                    val locationGranted = isLocationPermissionGranted(this)
                    locationManager =
                        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                    val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    val hasNetwork =
                        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)


                    val gpsLocationListener: LocationListener = object : LocationListener {
                        override fun onLocationChanged(location: Location) {
                            lattiude = location.latitude
                            longitutde = location.longitude
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

                            lattiude = precision.format(location.latitude).toDouble()
                            longitutde = precision.format(location.longitude).toDouble()
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

                        lattiude = precision.format(location.latitude).toDouble()
                        longitutde = precision.format(location.longitude).toDouble()
                    }

                    LaunchedEffect(key1 = executed) {
                        if (executed) {
                            while (processIndex <= 3 && processStart) {
                                delay(3000)
                                listOfProcessExcuted.add(processIndex)
                                processIndex++;
                                if (processIndex == 4) {
                                    processIndex = 3
                                    processStart = false
                                }
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color(0xFF0B1112))
                    ) {
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "RSA Emergency Assistance",
                            style = TextStyle(
                                color = Color.White,
                            )
                        )
                        Spacer(modifier = Modifier.size(10.dp))

                        if (selectedIndex != -1) {
                            Row(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                issueSelection(
                                    Modifier
                                        .weight(1f)
                                        .fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.size(5.dp))
                                MapBox(
                                    Modifier.weight(1f), this@MainActivity
                                )
                                Spacer(modifier = Modifier.size(5.dp))
                                selectedIssue(
                                    modifier = Modifier.weight(1f), executed,
                                    listOfProcessExcuted
                                )
                            }
                        } else {
                            SelectAnIssue(modifier = Modifier.fillMaxSize()) {
                                selectedIndex = it
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onMapReady(mapplsMap: MapplsMap) {
        val latLong: LatLng = LatLng(lattiude, longitutde)
        val markerOptions: MarkerOptions = MarkerOptions().position(latLong)
        var markerAdded by mutableStateOf(false)
        markerOptions.title = areaName
        markerOptions.snippet = address
        val markers = mapplsMap.markers
        if (markers.size > 0) {
            for (i in 0 until markers.size) {
                if (markers[i].title == areaName) {
                    markerAdded = true
                }
            }
        }
        if (!markerAdded && areaName.isNotEmpty()) {
            mapplsMap?.addMarker(markerOptions)
        }


        val latLongTechnician: LatLng = LatLng(lattiude - 0.0005, longitutde)
        val markerOptionsTechnician: MarkerOptions = MarkerOptions().position(latLongTechnician)
        var TechnicianmarkerAdded by mutableStateOf(false)
        if (processIndex >= 2) {
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
                lattiude, longitutde
            )
        ).zoom(15.0).tilt(0.0).build()
        mapplsMap.cameraPosition = cameraPosition

        // get Location Address
        val precision = DecimalFormat("0.0000")

        val lattitudeFromated = precision.format(lattiude).toDouble()
        val longitudeFromated = precision.format(longitutde).toDouble()
        if (areaName.isEmpty()) {
            getlocationAddress(lattitudeFromated, longitudeFromated)
        }

        // Not to Uncomment
//        val reverseGeoCode =
//            MapplsReverseGeoCode.builder().setLocation(lattiude, longitutde).build()
//        MapplsReverseGeoCodeManager.newInstance(reverseGeoCode)
//            .call(object : OnResponseCallback<PlaceResponse> {
//
//                override fun onSuccess(response: PlaceResponse) {
//                    areaName = response.places[0].locality
//                    address = response.places[0].formattedAddress
//
//                }
//
//                override fun onError(code: Int, message: String) {
//                Toast.makeText(
//                    this@MainActivity,
//                    message.toString(),
//                    Toast.LENGTH_SHORT
//                )
//                    .show()
//                }
//            })
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
                        areaName = addressResponse.results[0].locality
                        address = addressResponse.results[0].formatted_address
                    } else {
                        areaName = "Area Name"
                        address = "No Response"
                    }
                } else {
                    areaName = "Area Name"
                    address = "No Response"
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

@Composable
fun MapComponent(modifier: Modifier = Modifier, mainActivity: MainActivity) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    MapplsAccountManager.getInstance().restAPIKey = "4a0bbbe5ab800eca852f98bc67c7313b"
    MapplsAccountManager.getInstance().mapSDKKey = "4a0bbbe5ab800eca852f98bc67c7313b"
    MapplsAccountManager.getInstance().atlasClientId =
        "33OkryzDZsLz1faUP1SNrZP5d9Oo8yH_Ag8ko-lc_S-sMJj3F-VyMom7tOVYx2rP6KuofD3D900SGFrH5oNdIS7IyTIDUHf8"
    MapplsAccountManager.getInstance().atlasClientSecret =
        "lrFxI-iSEg_CDKS_hj7cdc1set2FQlVUHt5tz5JT3oNPDd-cWNKCmYetPlaiZ7Do4Vuecep5QTQiGAjH2wNjl1caGjvrAVNmspaGWbF8T74="
    Mappls.getInstance(LocalContext.current)


    val mapView = remember { MapView(context).apply { onCreate(null) } }
    mapView.getMapAsync(mainActivity)

    // Manage the lifecycle of the MapView
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> {}
            }
        }

        // Add observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // Cleanup when the effect leaves the composition
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            mapView.onDestroy()  // Proper cleanup of the MapView
        }
    }

    // AndroidView to display the MapView
    AndroidView(
        factory = { mapView }, modifier = modifier
    )
}

@Composable
fun issueSelection(modifier: Modifier) {
    Column(
        modifier = modifier,
    ) {

        val callGradient = Brush.verticalGradient(
            listOf(
                Color(44, 44, 44, 100),
                Color(236, 254, 238, 6)
            )
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(10.dp)
                    .weight(1f),
                text = "Select an issue or call us at ",
                style = TextStyle(
                    color = Color.White,
                )
            )
            Spacer(modifier = Modifier.size(4.dp))
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .background(
                        brush = callGradient, shape = RoundedCornerShape(
                            topStart = 30.dp, topEnd = 30.dp, bottomEnd = 30.dp, bottomStart = 30.dp
                        )

                    )
                    .border(
                        width = 1.dp, color = Color(0xFF3C4042), shape = RoundedCornerShape(
                            topStart = 30.dp, topEnd = 30.dp, bottomEnd = 30.dp, bottomStart = 30.dp
                        )
                    ), contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier.padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.call),
                        contentDescription = "",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Text(
                        text = "1800056743", style = TextStyle(
                            color = Color.White, fontFamily = FontFamily(Font(R.font.metropolis))
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.size(10.dp))
        LazyVerticalStaggeredGrid(
            modifier = Modifier.fillMaxWidth(),
            columns = StaggeredGridCells.Fixed(3)
        ) {
            itemsIndexed(listOfIssues) { index, issue ->
                issueBox(index, issue)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun issueBox(index: Int, issue: String) {
    var openDialog by remember { mutableStateOf(false) }

    if (openDialog) {
        AlertDialog(
            onDismissRequest = { openDialog = false },
            confirmButton = {
                Button(onClick = {
                    selectedIndex = index
                    executed = false
                    processIndex = 0
                    processStart = false
                    listOfProcessExcuted.clear()
                    openDialog = false
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = { openDialog = false }) {
                    Text("Dismiss")
                }
            },
            icon = {
                Icon(imageVector = Icons.Default.Warning, contentDescription = "Warning Icon")
            },
            title = {
                Text(text = "Cant Select", color = Color.Black)
            },
            text = {
                Text(
                    text = "Once of the issue is already selected, Cancel That Before you select This",
                    color = Color.DarkGray
                )
            },
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            containerColor = Color.White,
            iconContentColor = Color.Red,
            titleContentColor = Color.Black,
            textContentColor = Color.DarkGray,
            tonalElevation = 8.dp,
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false)
        )
    }

    Box(modifier = Modifier
        .size(150.dp)
        .fillMaxWidth()
        .clickable {
            if (processIndex == 0) {
                selectedIndex = index
                executed = false
                processIndex = 0
                listOfProcessExcuted.clear()
            } else {
                openDialog = true
            }
        }
        .padding(horizontal = 10.dp, vertical = 5.dp)
        .background(color = Color(0xFF1D3354), shape = RoundedCornerShape(10.dp))
        .border(
            width = 1.dp, color = if (selectedIndex == index) {
                Color(0xFF3DED4F)
            } else {
                Color.Transparent
            }, shape = RoundedCornerShape(10.dp)
        )) {
        if (selectedIndex == index) {
            Image(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.TopEnd)
                    .size(15.dp),
                painter = painterResource(id = R.drawable.circle_tick),
                contentDescription = ""
            )

        } else {
            Spacer(
                modifier = Modifier
                    .padding(10.dp)
                    .align(Alignment.TopEnd)
                    .size(15.dp)
                    .background(color = Color(0xFF0B1112), shape = CircleShape)
            )
        }
        Column(
            modifier = Modifier.padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(70.dp),
                painter = painterResource(id = listOfIssuesImage[index]),
                contentDescription = "",
                contentScale = ContentScale.FillBounds
            )
            Text(
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 10.dp)
                    .fillMaxWidth(),
                text = issue, style = TextStyle(
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            )
        }
    }
}


@Composable
fun MapBox(modifier: Modifier, mainActivity: MainActivity) {
    Column(modifier = modifier.padding(bottom = 10.dp)) {
        Text(
            modifier = Modifier.padding(10.dp), text = "Confirm Your Location", style = TextStyle(
                color = Color.White,
            )
        )
        Spacer(modifier = Modifier.size(10.dp))
        Box(modifier = Modifier) {
//            if(!mapIsReady) {
            MapComponent(
                mainActivity = mainActivity,
                modifier = Modifier
                    .background(
                        color = Color.Transparent, shape = RoundedCornerShape(10.dp)
                    )
                    .border(
                        width = 0.dp,
                        shape = RoundedCornerShape(10.dp),
                        color = Color.Transparent
                    )
            )
//            }
            if (!areaName.isEmpty()) {
                mapIsReady = true
                Column(
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.fillMaxHeight()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .background(color = Color.White, shape = RoundedCornerShape(4.dp))
                    ) {
                        Row {
                            Column(
                                modifier = Modifier.weight(2f),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Row(
                                ) {
                                    Spacer(
                                        modifier = Modifier
                                            .padding(start = 10.dp, top = 10.dp)
                                            .size(10.dp)
                                            .background(
                                                color = Color(0xFF34A443),
                                                shape = RoundedCornerShape(10.dp)
                                            )
                                    )
                                    Text(
                                        modifier = Modifier.padding(top = 10.dp, start = 10.dp),
                                        text = areaName,
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 10.sp
                                        )
                                    )
                                }
                                Text(
                                    modifier = Modifier.padding(
                                        horizontal = 30.dp,
                                        vertical = 10.dp
                                    ),
                                    text = address,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    style = TextStyle(
                                        color = Color.Black,
                                        fontSize = 10.sp

                                    )
                                )
                            }
                            if (processIndex >= 2) {
                                Column(
                                    modifier = Modifier
                                        .weight(1.4f)
                                        .padding(horizontal = 10.dp),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        modifier = Modifier.padding(
                                            vertical = 10.dp
                                        ),
                                        text = "My Tvs Assistance",
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 10.sp

                                        )
                                    )
                                    Text(
                                        modifier = Modifier,
                                        text = buildAnnotatedString {
                                            append("Request ID : ")
                                            withStyle(
                                                style = SpanStyle(
                                                    color = Color.Red,

                                                    )
                                            ) {
                                                append("123456789")
                                            }
                                        },
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        style = TextStyle(
                                            color = Color.Black,
                                            fontSize = 10.sp

                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun selectedIssue(
    modifier: Modifier,
    executedValue: Boolean,
    listOfProcessExcuted: ArrayList<Int>
) {

    val calendar = Calendar.getInstance()
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    val callGradient = Brush.verticalGradient(
        listOf(
            Color(0xFF255AF5).copy(alpha = 1f),
            Color(0xFF255AF5).copy(alpha = 1f),
            Color(0xFF255AF5).copy(alpha = 0.8f),
            Color(0xFF255AF5).copy(alpha = 0.7f),
        )
    )

    val transparentGradient = Brush.verticalGradient(
        listOf(
            Color.Transparent,
            Color.Transparent
        )
    )
    Column(modifier = modifier.padding(bottom = 10.dp)) {
        Text(
            modifier = Modifier.padding(10.dp), text = "Selected Issue", style = TextStyle(
                color = Color.White,
            )
        )
        Spacer(modifier = Modifier.size(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .background(color = Color(0xFF1D3354), shape = RoundedCornerShape(10.dp))
                .border(
                    width = 1.dp, color = Color(0xFF1F57E7), shape = RoundedCornerShape(10.dp)
                )
                .padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = listOfIssuesImage[selectedIndex]),
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    modifier = Modifier.weight(1f),
                    text = listOfIssues[selectedIndex],
                    style = TextStyle(
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                )
                Row(
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                            .background(
                                brush = if (!executed) callGradient else transparentGradient,
                                shape = RoundedCornerShape(
                                    topStart = 30.dp,
                                    topEnd = 30.dp,
                                    bottomEnd = 30.dp,
                                    bottomStart = 30.dp
                                )

                            )
                            .border(
                                width = 1.dp,
                                color = if (!executed) Color(0xFF3C4042) else Color.Transparent,
                                shape = RoundedCornerShape(
                                    topStart = 30.dp,
                                    topEnd = 30.dp,
                                    bottomEnd = 30.dp,
                                    bottomStart = 30.dp
                                )
                            ), contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier.padding(5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier.clickable {
                                    if (executed == false) {
                                        executed = true
                                    } else {
                                        executed = false
                                    }
                                    if (processStart == false) {
                                        processStart = true
                                    } else {
                                        processStart = false
                                    }
                                    if (!executed) {
                                        selectedIndex = selectedIndex
                                        executed = false
                                        processIndex = 0
                                        processStart = false
                                        listOfProcessExcuted.clear()
                                    }
                                },
                                text = if (!executed) "Confirm Request" else "Cancel Request",
                                style = TextStyle(
                                    color = Color.White,
                                    fontFamily = FontFamily(Font(R.font.metropolis))
                                )
                            )
                        }
                    }
                }
            }
        }
        Column {
            if (!executed) { // change to false
                Text(
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp),
                    text = "Your Location",
                    style = TextStyle(
                        color = Color.White,
                    )
                )
                if (!areaName.isEmpty()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(
                            modifier = Modifier
                                .padding(10.dp)
                                .size(10.dp)
                                .background(
                                    color = Color(0xFF34A443), shape = RoundedCornerShape(10.dp)
                                )
                        )
                        Text(
                            modifier = Modifier, text = areaName, style = TextStyle(
                                color = Color.White,
                            )
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            modifier = Modifier.padding(10.dp),
                            text = "$hour $minute",
                            style = TextStyle(
                                color = Color.White,
                            )
                        )
                    }
                } else {
                    Dialog(
                        onDismissRequest = { !areaName.isEmpty() },
                        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(100.dp)
                                .background(White, shape = RoundedCornerShape(8.dp))
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 30.dp),
                    text = address,
                    style = TextStyle(
                        color = Color.White,
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
            } else {
                process(modifier = Modifier.weight(1f), listOfProcessExcuted)
            }
        }
    }
}

@Composable
fun process(modifier: Modifier, listOfProcessExecuted: ArrayList<Int>) {
    val context = LocalContext.current
    var boxHeight by remember {
        mutableIntStateOf(0)
    }
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1f))
        LazyColumn(
        ) {
            itemsIndexed(listOfServiceTimeKey) { index, item ->
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ConstraintLayout(
                        modifier = Modifier
                            .fillMaxHeight()
                    ) {
                        val (box, spacer1, spacer2) = createRefs()
                        if (index != 0) {
                            Canvas(modifier = Modifier
                                .constrainAs(spacer1) {
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(box.top)
                                    top.linkTo(parent.top)
                                }
                                .height(((boxHeight / 2) + 21).dp)
                                .width(1.dp)) {
                                if (listOfProcessExcuted.contains(index) || processIndex == 3) {
                                    drawLine(
                                        color = Color(
                                            0xFF34A443
                                        ),
                                        start = Offset(0f, 0f),
                                        end = Offset(0f, size.height),
                                        strokeWidth = 1f
                                    )
                                } else {
                                    drawLine(
                                        color = Color.LightGray,
                                        start = Offset(0f, 0f),
                                        end = Offset(0f, size.height),
                                        pathEffect = PathEffect.dashPathEffect(
                                            floatArrayOf(10f, 10f),
                                            0f
                                        )
                                    )
                                }
                            }
                        }
                        if (listOfProcessExcuted.contains(index)) {
                            Image(
                                modifier = Modifier
                                    .constrainAs(box) {
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                        bottom.linkTo(parent.bottom)
                                        top.linkTo(parent.top)
                                    }
                                    .size(10.dp)
                                    .background(
                                        color = Color.LightGray,
                                        shape = RoundedCornerShape(10.dp)
                                    ),
                                painter = painterResource(id = R.drawable.circle_tick),
                                contentDescription = "")
                        } else if (processIndex == index) {
                            Image(
                                modifier = Modifier
                                    .constrainAs(box) {
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                        bottom.linkTo(parent.bottom)
                                        top.linkTo(parent.top)
                                    }
                                    .size(10.dp)
                                    .background(
                                        color = Color.LightGray,
                                        shape = RoundedCornerShape(10.dp)
                                    ),
                                painter = painterResource(id = R.drawable.processing),
                                contentDescription = "")
                        } else {
                            Spacer(modifier = Modifier
                                .constrainAs(box) {
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                    bottom.linkTo(parent.bottom)
                                    top.linkTo(parent.top)
                                }
                                .size(10.dp)
                                .background(
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(10.dp)
                                ))
                        }
                        if (index != 3) {
                            Canvas(
                                modifier = Modifier
                                    .constrainAs(spacer2) {
                                        start.linkTo(parent.start)
                                        end.linkTo(parent.end)
                                        top.linkTo(parent.top)
                                        top.linkTo(box.bottom)
                                    }
                                    .fillMaxHeight()
                                    .height(((boxHeight / 2) + 20).dp)
                                    .width(1.dp)
                            ) {

                                if (listOfProcessExcuted.contains(index)) {
                                    drawLine(
                                        color = if (listOfProcessExcuted.contains(index - 1) || (processIndex > -1 && index == 0) || processIndex == 3) Color(
                                            0xFF34A443
                                        ) else Color.LightGray,
                                        start = Offset(0f, 0f),
                                        end = Offset(0f, size.height),
                                        strokeWidth = 1f
                                    )
                                } else {
                                    drawLine(
                                        color = Color.LightGray,
                                        start = Offset(0f, 0f),
                                        end = Offset(0f, size.height),
                                        pathEffect = PathEffect.dashPathEffect(
                                            floatArrayOf(10f, 10f),
                                            0f
                                        )
                                    )
                                }
                            }
                        }
                    }
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                        }
                        .padding(horizontal = 20.dp, vertical = 5.dp)
                        .background(
                            color = Color(0xFF1D3354), shape = RoundedCornerShape(10.dp)
                        )
                        .border(
                            width = 1.dp, color = if (processIndex == index) {
                                Color(0xFF23AD13)
                            } else {
                                Color.Transparent
                            }, shape = RoundedCornerShape(10.dp)
                        )
                        .padding(10.dp)
                        .onGloballyPositioned {
                            boxHeight = (it.size.height / 2) - 10
                        }
                    ) {
                        Column(
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                text = item,
                                style = TextStyle(
                                    color = Color.White,
                                )
                            )
                            Text(
                                modifier = Modifier.padding(horizontal = 10.dp),
                                text = listOfServiceTimeValue[index],
                                maxLines = 1,
                                style = TextStyle(
                                    color = Color.White,
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
