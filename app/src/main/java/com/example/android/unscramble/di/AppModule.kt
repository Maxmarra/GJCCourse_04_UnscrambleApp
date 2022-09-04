package com.example.android.unscramble.di

import android.app.Application
import androidx.room.Room
import com.example.android.unscramble.data.PlayerRepository
import com.example.android.unscramble.data.PlayerRepositoryImpl
import com.example.android.unscramble.data.PlayerRoomDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providePlayerDatabase(app: Application): PlayerRoomDatabase {
        return Room.databaseBuilder(
            app,
            PlayerRoomDatabase::class.java,
            "player_database"
        ).fallbackToDestructiveMigration()
         .build()
    }

    @Provides
    @Singleton
    fun providePlayerRepository(db: PlayerRoomDatabase):
            PlayerRepository {
        return PlayerRepositoryImpl(db.dao)
    }
}