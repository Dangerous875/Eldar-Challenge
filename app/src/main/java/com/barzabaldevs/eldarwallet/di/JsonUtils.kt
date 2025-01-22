package com.barzabaldevs.eldarwallet.di

import com.google.gson.Gson
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JsonUtils @Inject constructor() {
    private val gson = Gson()

    fun <T> encodeToJson(data: T): String {
        return gson.toJson(data)
    }

    fun <T> decodeFromJson(json: String, type: Type): T {
        return gson.fromJson(json, type)
    }
}