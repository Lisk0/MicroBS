package com.lisko.microbs.utils.network

object Constants {
    //https://api.openweathermap.org/data/2.5/onecall?lat=44.787197&lon=20.457273&units=metric&exclude=minutely,hourly&appid=bdc8210004b28bd495ebf0d405af2754
    //https://api.openweathermap.org/data/2.5/onecall?lat={lat}&lon={lon}&exclude={part}&appid={API key}

    const val testbase= "http://api.openweathermap.org/"
    const val testendpoint= "data/2.5/onecall?lat=44.787197&lon=20.457273&units=metric&exclude=minutely,hourly&appid=bdc8210004b28bd495ebf0d405af2754"

    const val API_ENDPOINT= "data/2.5/onecall"
    const val LATITUDE= "lat"
    const val LONGITUDE= "lon"
    const val UNITS= "units"
    const val EXCLUDE= "exclude"
    const val API_ID= "appid"
    const val BASE_URL= "https://api.openweathermap.org/"


    const val LATITUDE_VALUE: Double= 44.787197
    const val LONGITUDE_VALUE: Double= 20.457273
    /*
    NEW YORK COORDINATES
    const val LATITUDE_VALUE: Double= 40.7128
    const val LONGITUDE_VALUE: Double= -74.006

     */
    const val UNITS_VALUE: String= "metric"
    const val EXCLUDE_VALUE: String= "minutely,hourly"
    const val API_KEY_VALUE= "bdc8210004b28bd495ebf0d405af2754"
}