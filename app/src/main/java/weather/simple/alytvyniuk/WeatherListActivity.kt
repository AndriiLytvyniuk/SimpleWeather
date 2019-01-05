package weather.simple.alytvyniuk

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem

import kotlinx.android.synthetic.main.activity_weather_list.*
import weather.simple.alytvyniuk.serverapi.ServerApi
import weather.simple.alytvyniuk.serverapi.model.City
import weather.simple.alytvyniuk.serverapi.model.CityWeather
import weather.simple.alytvyniuk.serverapi.model.CityWeatherDisplayed

class WeatherListActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "WeatherListActivity"
        private const val INTERNET_PERMISSION_ID = 101
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
//        if (!checkInternetPermission()) {
//            return
//        }
        val cityList = getCityList()
        Log.d("Andrii", ": request")
        ServerApi.instance.requestCityGroupWeather(object : ServerApi.ServerApiListener {
            override fun onSuccess(weathers: List<CityWeather>) {
                Log.d("Andrii", ": success ${weathers.size}")

                val weatherDisplayList : List<CityWeatherDisplayed> = weathers.map {
                    CityWeatherDisplayed(it.name,
                        it.sys.country,
                        it.weather[0].description,
                        Math.round(it.main.temp).toInt(),
                        it.weather[0].icon) }
                adapter.setCityWeathers(weatherDisplayList)
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

    private fun checkInternetPermission() : Boolean {
        Log.d("Andrii", ":checkInternetPermission ")
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
            != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {
                Log.d("Andrii", ":checkInternetPermission3 ")
            } else {
                Log.d("Andrii", ":checkInternetPermission2 ")
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.INTERNET), INTERNET_PERMISSION_ID)
            }
            return false
        }
        Log.d("Andrii", ":checkInternetPermission2 ")
        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            INTERNET_PERMISSION_ID -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    requestWeather()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_weather_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_refresh -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
