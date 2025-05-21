package com.proxod3.nogravityzone.ui.room

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.proxod3.nogravityzone.ui.models.UserStats

@Dao
interface StatsDao {
    @Query("SELECT * FROM stats WHERE userId = :userId")
    suspend fun getStatsByUserId(userId: String): UserStats?

    @Update
    suspend fun updateStats(stats: UserStats)
}