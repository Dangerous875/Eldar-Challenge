package com.barzabaldevs.eldarwallet.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.barzabaldevs.eldarwallet.data.database.entities.UserDataEntity

@Dao
interface UsersDao {

    @Query("SELECT * FROM user_table WHERE id = :id")
    suspend fun getUserById(id: String): UserDataEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserDataEntity)

    @Query("UPDATE user_table SET creditCards = :newCreditCards WHERE id = :id")
    suspend fun updateCreditCards(id: String, newCreditCards: String)

    @Query("UPDATE user_table SET balance = :newBalance WHERE id = :id")
    suspend fun updateUserBalance(id: String, newBalance: Double)
}