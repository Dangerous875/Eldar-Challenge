package com.barzabaldevs.eldarwallet.di

import android.util.Base64
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EncryptionUtils @Inject constructor(private val keyStoreUtils: KeyStoreUtils) {

    private val secretKey: SecretKey by lazy { keyStoreUtils.getOrCreateKey() }

    companion object {
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
    }

    fun encrypt(data: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encryptedBytes = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        val ivAndEncrypted = iv + encryptedBytes
        return Base64.encodeToString(ivAndEncrypted, Base64.DEFAULT)
    }

    fun decrypt(encryptedData: String): String {
        val decodedBytes = Base64.decode(encryptedData, Base64.DEFAULT)
        val iv = decodedBytes.copyOfRange(0, 12)
        val encryptedBytes = decodedBytes.copyOfRange(12, decodedBytes.size)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val spec = GCMParameterSpec(128, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes, Charsets.UTF_8)
    }
}