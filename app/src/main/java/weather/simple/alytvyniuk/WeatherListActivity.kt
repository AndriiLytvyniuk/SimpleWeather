package weather.simple.alytvyniuk

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_weather_list.*
import weather.simple.alytvyniuk.serverapi.ServerApi
import weather.simple.alytvyniuk.serverapi.model.City
import weather.simple.alytvyniuk.serverapi.model.CityWeatherDisplayed

class WeatherListActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "WeatherListActivity"
    }
    lateinit var adapter: CityListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_list)
        setSupportActionBar(toolbar)
        cities_list_view.layoutManager = LinearLayoutManager(this)
        adapter = CityListAdapter()
        cities_list_view.adapter = adapter
        requestWeather()
    }

    private fun requestWeather() {
        val cityList = getCityList()
        ServerApi.instance.requestCityGroupWeather(object : ServerApi.ServerApiListener {
            override fun onSuccess(weathers: List<CityWeatherDisplayed>) {
                adapter.setCityWeathers(weathers)
                adapter.notifyDataSetChanged()
            }

            override fun onError() {
                Log.d(TAG, ": error")
            }

        }, *cityList.toTypedArray())
    }

    private fun getCityList() : List<City> {
        return listOf(City("Kiev", 703448),
            City("London", 2643743),
            City("Toronto", 6167865)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_weather_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                requestWeather()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
