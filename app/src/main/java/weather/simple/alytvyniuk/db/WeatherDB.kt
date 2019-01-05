package weather.simple.alytvyniuk.db

import androidx.room.Database
import androidx.room.RoomDatabase
import weather.simple.alytvyniuk.serverapi.model.CityWeatherDisplayed

@Database(entities = [CityWeatherDisplayed::class], version = 1)
abstract class WeatherDB : RoomDatabase() {

    abstract fun getWeatherDao() : WeatherDisplayedDao
}