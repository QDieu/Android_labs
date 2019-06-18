package com.example.weatherapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import com.example.weatherapp.ItemFragment.OnListFragmentInteractionListener
import com.example.weatherapp.WeatherHelp.WeatherContent.WeatherDayItem

import kotlinx.android.synthetic.main.fragment_item.view.*

class MyItemRecyclerViewAdapter(
    private var mValues: MutableList<Pair<String, WeatherDayItem>>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as Pair<String, WeatherDayItem>
            val position = mValues.indexOf(item)
            mListener?.onListFragmentInteraction(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mDate.text = item.first
        val str = "Ощущается: " + item.second.feel_map["2"]
        holder.mFeel.text = str
        holder.mTemp.text = item.second.temp_map["2"]

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    fun clear() {
        mValues.clear()
        notifyDataSetChanged()
    }

    fun addAll(list: List<Pair<String, WeatherDayItem>>) {
        mValues.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mDate: TextView = mView.date
        val mTemp: TextView = mView.temperature
        val mFeel: TextView = mView.feel

        override fun toString(): String {
            return super.toString() + " '" + mTemp.text + "'"
        }
    }
}
