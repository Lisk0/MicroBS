package com.lisko.microbs.utils.network

import com.lisko.microbs.model.entities.ApiData
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET(Constants.API_ENDPOINT)
    fun getWeather(
        @Query(Constants.LATITUDE) latitude: Double,
        @Query(Constants.LONGITUDE) longitude: Double,
        @Query(Constants.UNITS) units: String,
        @Query(Constants.EXCLUDE) exclude: String,
        @Query(Constants.API_ID) apiKey: String
    ): Single<ApiData.ApiData>

    @GET(Constants.testendpoint)
    fun testData(): Single<ApiData.ApiData>
}