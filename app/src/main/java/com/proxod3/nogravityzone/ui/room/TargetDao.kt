package com.proxod3.nogravityzone.ui.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.proxod3.nogravityzone.ui.models.TargetMuscle

@Dao
interface TargetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(targets: List<TargetMuscle>)

    @Query("SELECT * FROM targets")
    suspend fun getAll(): List<TargetMuscle>
}