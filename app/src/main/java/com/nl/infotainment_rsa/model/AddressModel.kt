package com.nl.infotainment_rsa.model

data class AddressModel(
    val responseCode: Int,
    val results: List<Result>,
    val version: String
)