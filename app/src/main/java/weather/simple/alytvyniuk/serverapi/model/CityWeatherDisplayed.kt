package weather.simple.alytvyniuk.serverapi.model

data class CityWeatherDisplayed(val cityName : String,
                                val countryCode : String,
                                val weatherCondition : String,
                                val temperature : Int,
                                val weatherIconId : String)