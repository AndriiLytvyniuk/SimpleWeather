package weather.simple.alytvyniuk.dagger

import android.app.Application
import androidx.annotation.NonNull
import androidx.room.Room
import dagger.Module
import dagger.Provides
import weather.simple.alytvyniuk.db.WeatherDB
import weather.simple.alytvyniuk.serverapi.ServerApi
import javax.inject.Singleton

@Module
class WeatherModule(val application: Application) {

    @Provides
    @NonNull
    @Singleton
    fun provideServerApi() : ServerApi {
        return ServerApi.instance
    }

    @Provides
    @NonNull
    @Singleton
    fun provideWeatherDb() : WeatherDB {
        return Room.databaseBuilder(
            application,
            WeatherDB::class.java, WeatherDB.DB_NAME
        ).build()
    }
}