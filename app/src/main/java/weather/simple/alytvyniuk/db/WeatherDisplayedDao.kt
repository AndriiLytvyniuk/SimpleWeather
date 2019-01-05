package weather.simple.alytvyniuk.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import weather.simple.alytvyniuk.serverapi.model.CityWeatherDisplayed

@Dao
interface WeatherDisplayedDao {

    companion object {
        private const val WEATHER_TABLE_NAME = "CityWeatherDisplayed"
    }

    @Query("SELECT * FROM $WEATHER_TABLE_NAME")
    fun getAll() : List<CityWeatherDisplayed>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(weathers : List<CityWeatherDisplayed>)

}