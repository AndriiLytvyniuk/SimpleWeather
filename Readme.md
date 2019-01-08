# SimpleWeather

SimpleWeather is a small app that retrieves and displays weather for several cities.
It takes info from http://api.openweathermap.org/

# Features

  - There are 3 hardcoded cities: Kyiv, London, Toronto
  - If network is not available, data is taken from last successful request
  - Images have memory and disk cache
  - Data of request is saved into db by ROOM
  - There is a JobService, that checks latest data every hour
  - Dagger DI added for injecting network layer and db
  - There are several simple tests

# Build

As any other Android gradle project:
run *./gradlew build* or launch from AndroidStudio
