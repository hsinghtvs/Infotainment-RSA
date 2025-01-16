package com.example.infotainment_rsa.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.infotainment_rsa.R

class MainViewModel : ViewModel() {

    var openDialog by mutableStateOf(false)
    var openDialogPre by mutableStateOf(false)
    var isLocationGranted by mutableStateOf(false)
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
    var closeApp by mutableStateOf(false)
    var issueFromAndroid by mutableStateOf("No issues Found")
    var confirmLoading by mutableStateOf(false)
    var issueAndroidImage by mutableStateOf(R.drawable.rsa)


    val buttonStroke = Brush.linearGradient(
        listOf(
            Color(0xFFFFFFFF).copy(alpha = 1f),
            Color(0xFFFFFFFF).copy(alpha = 1f),
            Color(0xFFFFFFFF).copy(alpha = 1f),
            Color(0xFFFFFFFF).copy(alpha = 1f)
        )
    )

    val buttonGradient = Brush.verticalGradient(
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

    val callGradient = Brush.verticalGradient(
        listOf(
            Color(44, 44, 44, 100),
            Color(236, 254, 238, 6)
        )
    )

    init {
        addListOfServiceTimeValue()
        addListOfServiceTimeKey()
        addListOfImages()
        addListOfIssues()
    }

    private fun addListOfServiceTimeValue() {
        listOfServiceTimeValue.clear()
        listOfServiceTimeValue.add("Emergency Assist request in being processed on priority.")
        listOfServiceTimeValue.add("Emergency Assist request is assigned to Technician - Arun ")
        listOfServiceTimeValue.add("Arun is on the way to your location and will reach soon!")
        listOfServiceTimeValue.add("Arun has arrived and will guide you with the next steps")
    }

    private fun addListOfServiceTimeKey() {
        listOfServiceTimeKey.clear()
        listOfServiceTimeKey.add("Hang in there!")
        listOfServiceTimeKey.add("We've got you!")
        listOfServiceTimeKey.add("Help is on the way!")
        listOfServiceTimeKey.add("myTvs to the rescue!")
    }

    fun addListOfImages() {
        listOfIssuesImage.clear()
        listOfIssuesImage.add(R.drawable.braekdown)
        listOfIssuesImage.add(R.drawable.battery__1_)
        listOfIssuesImage.add(R.drawable.clutch)
        listOfIssuesImage.add(R.drawable.engine__new)
        listOfIssuesImage.add(R.drawable.flat_tyre)
        listOfIssuesImage.add(R.drawable.fuel_consumption)
        listOfIssuesImage.add(R.drawable.starting_prob)
        listOfIssuesImage.add(R.drawable.engine_overheat__new)
    }

    fun addListOfIssues() {
        listOfIssues.clear()
        listOfIssues.add("Accident")
        listOfIssues.add("Battery Jump Start")
        listOfIssues.add("Clutch/Brake Problem")
        listOfIssues.add("Engine")
        listOfIssues.add("Flat Tyre")
        listOfIssues.add("Fuel")
        listOfIssues.add("Starting Problem")
        listOfIssues.add("Engine OverHeating")

    }

}