package com.nicolas.data.di

import android.app.Application
import androidx.room.Room
import com.nicolas.data.local.TrackerDatabase
import com.nicolas.data.mapper.TrackableFoodMapper
import com.nicolas.data.mapper.TrackedFoodMapper
import com.nicolas.data.remote.OpenFoodApi
import com.nicolas.data.remote.OpenFoodApi.Companion.BASE_URL
import com.nicolas.data.repository.TrackerRepositoryImp
import com.nicolas.domain.repository.TrackerRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            ).build()
    }

    @Provides
    @Singleton
    fun provideOpenFoodApi(client: OkHttpClient): OpenFoodApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun providesTrackerDatabase(app: Application): TrackerDatabase {
        return Room.databaseBuilder(
            app,
            TrackerDatabase::class.java,
            "tracker_db"
        ).build()
    }

    @Provides
    @Singleton
    fun providesTrackerRepository(
        api: OpenFoodApi,
        db: TrackerDatabase
    ) : TrackerRepository {
        return  TrackerRepositoryImp(
            dao = db.dao,
            api = api,
            trackableFoodMapper = TrackableFoodMapper(),
            trackedFoodMapper = TrackedFoodMapper()
        )
    }
}