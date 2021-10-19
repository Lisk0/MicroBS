package com.lisko.microbs.view.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.lisko.microbs.databinding.WeatherListBinding
import com.lisko.microbs.model.entities.ApiData
import java.time.Instant
import java.util.*
import kotlin.math.roundToInt

class WeatherAdapter (private val fragment: Fragment) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {

    private var mWeatherList: List<ApiData.Daily> = listOf()

    class ViewHolder(view: WeatherListBinding) : RecyclerView.ViewHolder(view.root) {
        val weatherMin= view.tvWeatherMin
        val weatherMax= view.tvWeatherMax
        val weatherDatum= view.tvWeatherDatum
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAdapter.ViewHolder {
        val binding: WeatherListBinding = WeatherListBinding.inflate(
            LayoutInflater.from(fragment.context),
            parent, false
        )
        return WeatherAdapter.ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return mWeatherList.size
    }

    fun setDailyList(list: List<ApiData.Daily>) {
        mWeatherList = list
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataDaily = mWeatherList[position]
        val minW= dataDaily.temp.min.roundToInt().toString() + " °C"
        val maxW= dataDaily.temp.max.roundToInt().toString() + " °C"
        holder.weatherMin.text= minW
        holder.weatherMax.text= maxW
        val date= Date(dataDaily.dt.toLong()*1000)

        val splited= date.toString().split(" ")
        var formatedDate= splited[0] +" "+ splited[1]+" "+ splited[2]+" "+ splited.last()
        holder.weatherDatum.text= formatedDate
    }
}