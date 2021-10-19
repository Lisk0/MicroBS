package com.lisko.microbs.viewmodel


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lisko.microbs.model.entities.ApiData
import com.lisko.microbs.utils.network.WeatherAPIService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class WeatherViewModel : ViewModel() {


    private val weatherAPIService = WeatherAPIService()
    private val compositeDisposable= CompositeDisposable()

    val loadWeather = MutableLiveData<Boolean>()
    val weatherResponse= MutableLiveData<ApiData.ApiData>()
    val weatherLoadingError= MutableLiveData<Boolean>()

    fun getWeatherFromAPI(){
        loadWeather.value= true

        compositeDisposable.add(
            weatherAPIService.getWeather()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<ApiData.ApiData>() {
                    override fun onSuccess(value: ApiData.ApiData) {
                        loadWeather.value= false
                        weatherLoadingError.value= false
                        weatherResponse.value= value
                    }

                    override fun onError(e: Throwable) {
                        loadWeather.value= false
                        weatherLoadingError.value= true
                        Log.e("greska", e.message!!)

                    }
                })
        )

    }

    fun getWeatherTestAPI(){
        loadWeather.value= true

        compositeDisposable.add(
            weatherAPIService.test()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<ApiData.ApiData>() {
                    override fun onSuccess(value: ApiData.ApiData) {
                        loadWeather.value= false
                        weatherLoadingError.value= false
                        weatherResponse.value= value
                    }

                    override fun onError(e: Throwable) {
                        loadWeather.value= false
                        weatherLoadingError.value= true
                        Log.e("greska", e.message!!)

                    }
                })
        )

    }

}