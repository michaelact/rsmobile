// data/local/Converters.kt
package com.nightwolf.rsmobile.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.nightwolf.rsmobile.data.model.Coordinates

class Converters {
    @TypeConverter
    fun fromCoordinates(coordinates: Coordinates?): String? {
        return coordinates?.let { Gson().toJson(it) }
    }

    @TypeConverter
    fun toCoordinates(value: String?): Coordinates? {
        return value?.let { Gson().fromJson(it, Coordinates::class.java) }
    }
}
