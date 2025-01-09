package com.example.infotainment_rsa.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.infotainment_rsa.R

class MainViewModel : ViewModel() {

    var openDialog by mutableStateOf(false)
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

    init {
        addListOfServiceTimeValue()
        addListOfServiceTimeKey()
        addListOfImages()
        addListOfIssues()
    }

    private fun addListOfServiceTimeValue() {
        listOfServiceTimeValue.clear()
        listOfServiceTimeValue.add("Emergency Assist request in being processed on priority.")
        listOfServiceTimeValue.add("Emergency Assist request is assigned to the nearest available technician.")
        listOfServiceTimeValue.add("myTVS Technician is on the way to your location and will reach soon!")
        listOfServiceTimeValue.add("The technician has arrived and will guide you with the next steps")
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
        listOfIssuesImage.add(R.drawable.lostkey)
        listOfIssuesImage.add(R.drawable.flat_tyre)
        listOfIssuesImage.add(R.drawable.fuel_consumption)
        listOfIssuesImage.add(R.drawable.braekdown)
        listOfIssuesImage.add(R.drawable.engine_overheat)
    }

    fun addListOfIssues() {
        listOfIssues.clear()
        listOfIssues.add("Accident")
        listOfIssues.add("Battery Jump Start")
        listOfIssues.add("Clutch/Break Problem")
        listOfIssues.add("Lost/ Loacked Keys")
        listOfIssues.add("Flat Tyre")
        listOfIssues.add("Fuel Problem")
        listOfIssues.add("BreakDown")
        listOfIssues.add("Engine OverHeating")

    }

}