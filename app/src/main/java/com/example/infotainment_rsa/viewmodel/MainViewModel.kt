package com.example.infotainment_rsa.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

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

}