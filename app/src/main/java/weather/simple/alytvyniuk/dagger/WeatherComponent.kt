package weather.simple.alytvyniuk.dagger

import androidx.annotation.NonNull
import dagger.Component
import weather.simple.alytvyniuk.WeatherJobService
import weather.simple.alytvyniuk.WeatherListViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [WeatherModule::class])
public interface WeatherComponent {
    fun inject(@NonNull weatherListViewModel: WeatherListViewModel)
    fun inject(@NonNull weatherJobService: WeatherJobService)
}