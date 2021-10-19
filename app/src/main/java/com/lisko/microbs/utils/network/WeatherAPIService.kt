package com.lisko.microbs.utils.network

import com.lisko.microbs.model.entities.ApiData
import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherAPIService {
    private val api= Retrofit.Builder().baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    fun getWeather(): Single<ApiData.ApiData>{
        return api.getWeather(
            Constants.LATITUDE_VALUE,
            Constants.LONGITUDE_VALUE,
            Constants.UNITS_VALUE,
            Constants.EXCLUDE_VALUE,
            Constants.API_KEY_VALUE
        )
    }

    fun test(): Single<ApiData.ApiData>{
        return api.testData()
    }
}