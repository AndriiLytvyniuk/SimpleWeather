package weather.simple.alytvyniuk.dagger

import android.app.Application
import androidx.annotation.NonNull
import androidx.room.Room
import dagger.Module
import dagger.Provides
import weather.simple.alytvyniuk.db.WeatherDB
import weather.simple.alytvyniuk.serverapi.IServerApi
import weather.simple.alytvyniuk.serverapi.model.City
import javax.inject.Singleton

@Module
class WeatherModule(private val application: Application) {

    @Provides
    @NonNull
    @Singleton
    fun provideServerApi() : IServerApi {
        return object: IServerApi {
            override fun requestCityGroupWeather(listener: IServerApi.ServerApiListener, cities: List<City>) {
                listener.onError()
            }
        }
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