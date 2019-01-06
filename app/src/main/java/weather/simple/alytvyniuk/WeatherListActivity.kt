package weather.simple.alytvyniuk

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.NonNull
import kotlinx.android.synthetic.main.activity_weather_list.*
import weather.simple.alytvyniuk.serverapi.model.City
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class WeatherListActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "WeatherListActivity"
    }
    private lateinit var adapter: CityListAdapter
    private lateinit var model : WeatherListViewModel

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
        model = ViewModelProviders.of(this).get(WeatherListViewModel::class.java)
        model.citiesWeather.observe(this, Observer<@NonNull WeatherListViewModel.ResponseWrapper> { responseWrapper ->
            showProgress(false)
            val time = if (responseWrapper.data.isNotEmpty()) responseWrapper.data[0].time else null
            showNoNetworkLayout(responseWrapper.hasNetworkError, time)
            adapter.setCityWeathers(responseWrapper.data)
            adapter.notifyDataSetChanged()
        })

        updateWeather()
    }

    private fun updateWeather() {
        showProgress(true)
        val cityList = getCityList()
        model.requestWeatherDisplayed(cityList)
    }

    private fun showProgress(shouldShow: Boolean) {
        progress_circle.visibility = if (shouldShow) View.VISIBLE else View.GONE
        invalidateOptionsMenu()
    }

    private fun showNoNetworkLayout(shouldShow : Boolean, time : Long?) {
        if (shouldShow) {
            if (time == null) {
                tv_offline_time.text = getString(R.string.offline_mode)
            } else {
                val calendar = Calendar.getInstance()
                calendar.time = Date(time)
                val hours = calendar.get(Calendar.HOUR_OF_DAY)
                val minutes = calendar.get(Calendar.MINUTE)
                tv_offline_time.text = getString(R.string.offline_mode_with_time, hours, minutes)
            }
            tv_offline_time.visibility = View.VISIBLE
        } else {
            tv_offline_time.visibility = View.GONE
        }
    }

    private fun getCityList() : List<City> {
        return listOf(City("Kiev", 703448),
            City("London", 2643743),
            City("Toronto", 6167865)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_weather_list, menu)
        menu.findItem(R.id.action_refresh).isVisible = progress_circle.visibility == View.GONE
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
