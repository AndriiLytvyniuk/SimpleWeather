package weather.simple.alytvyniuk

import androidx.recyclerview.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import weather.simple.alytvyniuk.serverapi.model.CityWeatherDisplayed
import kotlinx.android.synthetic.main.city_list_item.view.*

class CityListAdapter : Adapter<CityListAdapter.CityItemHolder>() {

    companion object {
        private const val BASE_IMAGES_URL = "http://openweathermap.org/img/w/"
    }

    private var cityWeathers : List<CityWeatherDisplayed> = listOf()

    fun setCityWeathers(list : List<CityWeatherDisplayed>) {
        cityWeathers = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int)
        = CityItemHolder(parent.inflateWithoutAttach(R.layout.city_list_item))

    override fun getItemCount() = cityWeathers.size

    override fun onBindViewHolder(holder: CityItemHolder, index: Int) = holder.bind(cityWeathers[index])

    fun ViewGroup.inflateWithoutAttach(layoutRes: Int): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, false)
    }

    class CityItemHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

        fun bind(item : CityWeatherDisplayed) {
            itemView.tv_city.text = itemView.context.getString(R.string.city_country, item.cityName, item.countryCode)
            itemView.tv_temperature.text = itemView.context.getString(R.string.temperature, item.temperature)
            itemView.tv_weather.text = item.weatherCondition
            Picasso.get()
                .load(BASE_IMAGES_URL + item.weatherIconId + ".png")
                .into(itemView.iv_weather_icon)
        }
    }
}