package com.barzabaldevs.eldarwallet.di

import com.barzabaldevs.eldarwallet.data.remote.QRCodeApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkProvider {

    private const val BASE_URL = "https://qrcode68.p.rapidapi.com/"

    @Provides
    @Singleton
    fun retrofitProvider(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): QRCodeApiService =
        retrofit.create(QRCodeApiService::class.java)

}