package com.example.android.unscramble.data

import android.content.ClipData
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {

    fun getPlayersRepo(): Flow<List<Player>>

    suspend fun insertRepo(player: Player)
}