package com.barzabaldevs.eldarwallet.di

import android.content.Context
import androidx.room.Room
import com.barzabaldevs.eldarwallet.data.database.DataBaseUsers
import com.barzabaldevs.eldarwallet.data.database.dao.UsersDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomProvider {

    private const val DATABASE_NAME = "Users-db"

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): DataBaseUsers =
        Room.databaseBuilder(context, DataBaseUsers::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideCharacterDao(roomDatabase: DataBaseUsers): UsersDao =
        roomDatabase.usersDao()
}