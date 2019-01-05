package weather.simple.alytvyniuk

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_weather_list.*
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
        cities_list_view.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        cities_list_view.addItemDecoration(
            androidx.recyclerview.widget.DividerItemDecoration(
                this,
                androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
            )
        )
        adapter = CityListAdapter()
        cities_list_view.adapter = adapter
        updateWeather()
    }

    private fun updateWeather() {
        val cityList = getCityList()
        val model = ViewModelProviders.of(this).get(WeatherListViewModel::class.java)
        model.getWeatherDisplayed(cityList).observe(this, Observer<List<CityWeatherDisplayed>> { weathers ->
            if (weathers != null) {
                adapter.setCityWeathers(weathers)
                adapter.notifyDataSetChanged()
            }
        })
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
                updateWeather()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
