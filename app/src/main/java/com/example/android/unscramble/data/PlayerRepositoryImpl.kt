package com.example.android.unscramble.data

import kotlinx.coroutines.flow.Flow

class PlayerRepositoryImpl(private val daoImpl:PlayerDao)
    :PlayerRepository{

    override fun getPlayersRepo(): Flow<List<Player>> {
        return daoImpl.getPlayersDao()
    }

    override suspend fun insertRepo(player: Player) {
        daoImpl.insertDao(player)
    }
}