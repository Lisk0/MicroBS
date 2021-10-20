package com.lisko.microbs.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lisko.microbs.R
import com.lisko.microbs.databinding.FragmentWeatherBinding
import com.lisko.microbs.model.entities.ApiData
import com.lisko.microbs.view.adapters.WeatherAdapter
import com.lisko.microbs.viewmodel.WeatherViewModel
import kotlin.math.roundToInt

class WeatherFragment : Fragment() {

    private lateinit var weatherViewModel: WeatherViewModel
    private var _binding: FragmentWeatherBinding? = null
    private val adapter= WeatherAdapter(this@WeatherFragment)

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        weatherViewModel =
            ViewModelProvider(this).get(WeatherViewModel::class.java)

        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherViewModel.getWeatherFromAPI()
        weatherViewModelObserver()

    }

    private fun weatherViewModelObserver(){
        weatherViewModel.weatherResponse.observe(viewLifecycleOwner, {
            response -> response?.let {
                Log.i("weather response", "${response.current}")
            binding.rvDaily.layoutManager= LinearLayoutManager(requireContext(),
                RecyclerView.VERTICAL, false)
            var mList: MutableList<ApiData.Daily> = mutableListOf()

            for(i in 1..3){
                mList.add(response.daily[i])
        }

            adapter.setDailyList(mList as List<ApiData.Daily>)
            binding.rvDaily.adapter= adapter

            binding.weatherGrad.text= response.timezone
            val trenutna= response.current.temp.roundToInt().toString() + " °C"
            binding.weatherTrenutna.text= trenutna
            val imgUrl: String= "https://openweathermap.org/img/wn/"+ response.current.weather.first().icon+ "@2x.png"
            Log.i("url", imgUrl)
            Glide.with(this).load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(binding.ivCurrentIcon)

        }
        })

        weatherViewModel.weatherLoadingError.observe(viewLifecycleOwner, {
            error -> error?.let {
                Log.e("weather loading error", error.toString())
            if(error){
                //binding.layoutNotLoaded.visibility= View.VISIBLE
                binding.layoutLoaded.visibility= View.GONE
            }
            else{
                //binding.layoutNotLoaded.visibility= View.GONE
                binding.layoutLoaded.visibility= View.VISIBLE
            }
        }
        })

        weatherViewModel.loadWeather.observe(viewLifecycleOwner, {
            loading -> loading?.let {
                Log.i("weather loading", "{$loading}")
        }
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}