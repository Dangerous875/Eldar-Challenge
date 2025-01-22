package com.barzabaldevs.eldarwallet.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.barzabaldevs.eldarwallet.domain.model.CreditCard
import com.barzabaldevs.eldarwallet.domain.model.UserModel

@Entity(tableName = "User_Table")
data class UserDataEntity(
    @PrimaryKey
    val id : String,
    @ColumnInfo(name = "email")
    val email : String,
    @ColumnInfo(name = "name")
    val name : String,
    @ColumnInfo(name = "lastName")
    val lastName : String,
    @ColumnInfo(name = "balance")
    val balance : Double,
    @ColumnInfo(name = "creditCards")
    val creditCards : String
) {
    fun toDomain(decodeCars: List<CreditCard>): UserModel {
        return UserModel(
            id = id,
            email = email,
            name = name,
            lastName = lastName,
            balance = balance,
            creditCards = decodeCars
        )
    }
}
