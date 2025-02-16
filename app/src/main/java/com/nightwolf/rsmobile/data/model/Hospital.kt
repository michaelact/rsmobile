// data/model/Hospital.kt
package com.nightwolf.rsmobile.data.model

data class Hospital(
    val name: String,
    val address: String,
    val region: String,
    val phone: String,
    val province: String,
    var distance: Double? = null,
    var coordinates: Coordinates? = null,
    val imageUrl: String = "https://cdn.pixabay.com/photo/2020/10/30/17/48/hospital-5699287_1280.jpg"
)

data class Coordinates(
    val latitude: Double,
    val longitude: Double
)

data class GeocodingResponse(
    val type: String,
    val features: List<Feature>
)

data class Feature(
    val geometry: Geometry,
    val properties: Properties,
    val place_name: String
)

data class Geometry(
    val coordinates: List<Double>
)

data class Properties(
    val categories: List<String>?,
    val phone: String?
)
