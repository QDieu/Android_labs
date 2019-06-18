package com.example.weatherapp.WeatherHelp

import android.content.Context
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import com.example.weatherapp.ItemFragment
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataHelper constructor(val context: Context) {

    private val KEY = "WeatherInfo"

    fun saveWeatherInfo() {
        val prefEditor = PreferenceManager.getDefaultSharedPreferences(context)
        val jsonString: String = Gson().toJson(WeatherContent.ITEMS)
        prefEditor.edit().putString(KEY, jsonString).apply()
    }

    fun getWeatherInfo(fragment: ItemFragment) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val jsonString = preferences.getString(KEY, null)

        if (jsonString == null) {
            getData(fragment)
        } else {
            WeatherContent.ITEMS =
                Gson().fromJson(jsonString, object : TypeToken<MutableList<WeatherContent.WeatherItem>>() {}.type)
            WeatherContent.processingData()
            fragment.setList(WeatherContent.INFO.toList().sortedBy { it.first })
            fragment.swipeContainer.isRefreshing = false
        }

    }

    fun getData(fragment: ItemFragment) {
        val apiHelper = ApiHelper.create()

        apiHelper.getWeatherInfo(24)
            .enqueue(object : Callback<List<WeatherContent.WeatherItem>> {
                override fun onFailure(call: Call<List<WeatherContent.WeatherItem>>, t: Throwable) {
                    Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show()
                    fragment.swipeContainer.isRefreshing = false
                }

                override fun onResponse(
                    call: Call<List<WeatherContent.WeatherItem>>,
                    response: Response<List<WeatherContent.WeatherItem>>
                ) {
                    if (response.code() == 200) {
                        val weatherResponse = response.body()!!
                        WeatherContent.ITEMS = weatherResponse.toMutableList()
                        WeatherContent.processingData()
                        fragment.setList(WeatherContent.INFO.toList().sortedBy { it.first })
                        val str: String = Gson().toJson(WeatherContent.ITEMS)
                        saveWeatherInfo()
                        fragment.swipeContainer.isRefreshing = false
                    }
                }
            })
    }
}