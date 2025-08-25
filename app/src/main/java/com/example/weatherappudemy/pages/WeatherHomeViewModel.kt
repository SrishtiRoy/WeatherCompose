package com.example.weatherappudemy.pages

import CurrentWeather
import android.app.Application
import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.weatherappudemy.data.ConnectivityRepository
import com.example.weatherappudemy.data.DefaultConnectivityRepository
import com.example.weatherappudemy.data.ForecastWeather
import com.example.weatherappudemy.data.WeatherRepository
import com.example.weatherappudemy.data.WeatherRepositoryImpl
import com.example.weatherappudemy.utils.WEATHER_API_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherHomeViewModel @Inject constructor (
    private val connectivityRepository: ConnectivityRepository,
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    var uiState: WeatherHomeUiState by mutableStateOf(WeatherHomeUiState.Loading)
    private var latitude = 0.0
    private var longitude = 0.0
    val connectivityState: StateFlow<ConnectivityState> = connectivityRepository.connectivityState
    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        uiState = WeatherHomeUiState.Error
    }

    fun setLocation(lat: Double, long: Double) {
        latitude = lat
        longitude = long
    }

    fun getWeatherData() {
        viewModelScope.launch(exceptionHandler) {
            uiState = try {
                val currentWeather = async { getCurrentData() }.await()
                val forecastWeather = async { getForecastData() }.await()

                WeatherHomeUiState.Success(Weather(currentWeather, forecastWeather))
            } catch (e: Exception) {
                Log.e("WeatherHomeViewModel", e.message.toString(),)
                WeatherHomeUiState.Error
            }
        }
    }

    private suspend fun getCurrentData() : CurrentWeather {
        val endUrl = "weather?lat=$latitude&lon=$longitude&appid=$WEATHER_API_KEY&units=imperial";
        return weatherRepository.getCurrentWeather(endUrl)
    }

    private suspend fun getForecastData() : ForecastWeather {
        val endUrl = "forecast?lat=$latitude&lon=$longitude&appid=$WEATHER_API_KEY&units=imperial";
        return weatherRepository.getForecastWeather(endUrl)
    }

    /*companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as Application)
                val connectivityManager = application.getSystemService(ConnectivityManager::class.java)
                WeatherHomeViewModel(
                    connectivityRepository = DefaultConnectivityRepository(connectivityManager)
                )
            }
        }
    }*/
}

