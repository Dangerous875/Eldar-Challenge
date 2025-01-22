package com.barzabaldevs.eldarwallet.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.barzabaldevs.eldarwallet.data.database.dao.UsersDao
import com.barzabaldevs.eldarwallet.data.remote.service.ApiServiceQR
import com.barzabaldevs.eldarwallet.di.EncryptionUtils
import com.barzabaldevs.eldarwallet.di.JsonUtils
import com.barzabaldevs.eldarwallet.domain.Repository
import com.barzabaldevs.eldarwallet.domain.model.CreditCard
import com.barzabaldevs.eldarwallet.domain.model.UserModel
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val usersDao: UsersDao,
    private val apiServiceQR: ApiServiceQR,
    private val encryptionUtils: EncryptionUtils,
    private val jsonUtils: JsonUtils
) : Repository {

    override suspend fun insertUser(user: UserModel) {
        val encodeCards = jsonUtils.encodeToJson(user.creditCards)
        val encryptCards = encryptionUtils.encrypt(encodeCards)
        usersDao.insertUser(user.toDataBase(encryptCards))
    }

    override suspend fun getUserById(id: String): UserModel {
        val user = usersDao.getUserById(id)
        val decryptCars = encryptionUtils.decrypt(user.creditCards)
        val type = object : TypeToken<List<CreditCard>>() {}.type
        val decodeCars = jsonUtils.decodeFromJson<List<CreditCard>>(decryptCars, type)
        usersDao.getUserById(id).let { return it.toDomain(decodeCars) }
    }

    override suspend fun updateCreditCardUser(id: String, card: List<CreditCard>) {
        val encodeCards = jsonUtils.encodeToJson(card)
        val encryptCards = encryptionUtils.encrypt(encodeCards)
        usersDao.updateCreditCards(id, encryptCards)

    }

    override suspend fun getQRCode(): Bitmap? {
        return withContext(Dispatchers.IO) {
            val response = apiServiceQR.generateQRCode()
            if (response.isSuccessful) {
                response.body()?.byteStream()?.use { inputStream ->
                    BitmapFactory.decodeStream(inputStream)
                }
            } else {
                null
            }
        }
    }
}