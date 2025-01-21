package com.barzabaldevs.eldarwallet.di

import com.barzabaldevs.eldarwallet.data.RepositoryImpl
import com.barzabaldevs.eldarwallet.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindRepository(repository: RepositoryImpl): Repository
}