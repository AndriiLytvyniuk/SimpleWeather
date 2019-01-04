package weather.simple.alytvyniuk.serverapi.model

data class CityGroupWeather(
    val cnt: Int,
    val list: List<CityWeather>
)

data class CityWeather(
    val id: Int,
    val main: Main,
    val name: String,
    val visibility: Int,
    val weather: List<Weather>
)

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class Main(
    val temp: Int
)