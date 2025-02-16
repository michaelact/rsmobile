package com.nightwolf.rsmobile.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.nightwolf.rsmobile.data.model.Hospital

@Database(
    entities = [Hospital::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun hospitalDao(): HospitalDao
}
