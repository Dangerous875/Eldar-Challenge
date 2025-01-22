package com.barzabaldevs.eldarwallet.di

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KeyStoreUtils @Inject constructor() {

    companion object {
        private const val ANDROID_KEYSTORE = "AndroidKeyStore"
        private const val KEY_ALIAS = "MyAppKeyAlias"
    }

    fun getOrCreateKey(): SecretKey {
        val existingKey = getKey()
        return existingKey ?: generateAndStoreKey()
    }

    private fun generateAndStoreKey(): SecretKey {
        val keyGen = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEYSTORE)
        val keyGenSpec = KeyGenParameterSpec.Builder(
            KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        )
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()
        keyGen.init(keyGenSpec)
        return keyGen.generateKey()
    }

    private fun getKey(): SecretKey? {
        val keyStore = java.security.KeyStore.getInstance(ANDROID_KEYSTORE)
        keyStore.load(null)
        return keyStore.getKey(KEY_ALIAS, null) as? SecretKey
    }
}