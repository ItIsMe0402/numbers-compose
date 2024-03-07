package com.github.itisme0402.numberscompose

import android.content.Context
import com.github.itisme0402.numberscompose.core.repo.LocalNumbersDataSource
import com.github.itisme0402.numberscompose.core.repo.NumbersRepository
import com.github.itisme0402.numberscompose.core.repo.NumbersRepositoryImpl
import com.github.itisme0402.numberscompose.core.repo.RemoteNumbersDataSource
import com.github.itisme0402.numberscompose.db.AppDatabase
import com.github.itisme0402.numberscompose.db.RoomNumbersDataSource
import com.github.itisme0402.numberscompose.network.NumbersApiNumbersDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module(includes = [SingletonModule.Binders::class])
@InstallIn(SingletonComponent::class)
class SingletonModule {
    @Provides
    fun provideDb(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.create(appContext)
    }

    @Provides
    fun provideNumbersDao(appDatabase: AppDatabase) = appDatabase.numbersDao()

    @Module
    @InstallIn(SingletonComponent::class)
    interface Binders {
        @Binds
        fun bindRemoteNumbersDataSource(dataSource: NumbersApiNumbersDataSource): RemoteNumbersDataSource

        @Binds
        fun bindLocalNumbersDataSource(dataSource: RoomNumbersDataSource): LocalNumbersDataSource

        @Binds
        fun bindNumbersRepository(repo: NumbersRepositoryImpl): NumbersRepository
    }
}