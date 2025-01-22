package com.barzabaldevs.eldarwallet.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.barzabaldevs.eldarwallet.data.database.dao.UsersDao
import com.barzabaldevs.eldarwallet.data.database.entities.UserDataEntity

@Database(entities = [UserDataEntity::class], version = 1)
abstract class DataBaseUsers : RoomDatabase(){
    abstract fun usersDao(): UsersDao
}