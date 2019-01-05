package weather.simple.alytvyniuk.serverapi.model

data class CityGroupWeather(
    val cnt: Int,
    val list: List<CityWeather>
)

data class CityWeather(
    val clouds: Clouds,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
)

data class Wind(
    val deg: Int,
    val speed: Double
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
    val message: Double,
    val sunrise: Int,
    val sunset: Int,
    val type: Int
)

data class Clouds(
    val all: Int
)

data class Main(
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
)

data class Coord(
    val lat: Double,
    val lon: Double
)