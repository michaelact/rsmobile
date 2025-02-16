// data/local/HospitalDao.kt
package com.nightwolf.rsmobile.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nightwolf.rsmobile.data.model.Hospital

@Dao
interface HospitalDao {
    @Query("SELECT * FROM hospitals")
    suspend fun getAllHospitals(): List<Hospital>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(hospitals: List<Hospital>)
}
