package com.example.infotainment_rsa.model

data class Result(
    val area: String,
    val city: String,
    val district: String,
    val formatted_address: String,
    val houseName: String,
    val houseNumber: String,
    val isRooftop: Boolean,
    val lat: String,
    val lng: String,
    val locality: String,
    val pincode: String,
    val poi: String,
    val poi_dist: String,
    val state: String,
    val street: String,
    val street_dist: String,
    val subDistrict: String,
    val subLocality: String,
    val subSubLocality: String,
    val village: String
)