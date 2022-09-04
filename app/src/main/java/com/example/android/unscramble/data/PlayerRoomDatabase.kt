package com.example.android.unscramble.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Player::class],
    version = 1,
    exportSchema = false)
abstract class PlayerRoomDatabase : RoomDatabase() {

    abstract val dao: PlayerDao

}