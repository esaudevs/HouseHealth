package com.esaudev.househealth.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesHouseHealthDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        HouseHealthDatabase::class.java,
        "house_health_database"
    )
        .fallbackToDestructiveMigration()
        .build()
}