package weather.simple.alytvyniuk.serverapi.model

data class CityGroupWeather(
    val cnt: Int,
    val list: List<CityWeather>
)

data class CityWeather(
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>
)

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class Sys(
    val country: String,
    val id: Int,
    val message: Double
)

data class Main(
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)