package weather.simple.alytvyniuk.serverapi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CityWeatherDisplayed(@PrimaryKey val cityCode : Int,
                                @ColumnInfo val cityName : String,
                                @ColumnInfo val countryCode : String,
                                @ColumnInfo val weatherCondition : String,
                                @ColumnInfo val temperature : Int,
                                @ColumnInfo val weatherIconId : String,
                                @ColumnInfo val time : Long)