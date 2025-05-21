package com.proxod3.nogravityzone.ui.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.proxod3.nogravityzone.ui.models.Equipment

@Dao
interface EquipmentDao {
    @Query("SELECT * FROM equipment")
    fun getAll(): List<Equipment>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(equipmentList: List<Equipment>)
}



