package com.hanamin.weather.ui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hanamin.weather.R
import com.hanamin.weather.data.remote.responce.fiveDailyWeather.ListModel
import com.hanamin.weather.databinding.ItemDetailWeatherBinding
import com.hanamin.weather.ui.viewmodel.ItemDetailWeatherVm

class DetailWeatherAdapter(private val list: MutableList<ListModel?>) :
    RecyclerView.Adapter<DetailWeatherAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_detail_weather,
            FrameLayout(parent.context), false
        )
        return DataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val dataModel: ListModel? = list[position]
        holder.setViewModel(ItemDetailWeatherVm(dataModel))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onViewAttachedToWindow(holder: DataViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.bind()
    }

    override fun onViewDetachedFromWindow(holder: DataViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.unbind()
    }

    fun updateData(data: MutableList<ListModel?>) {
        if (!data.isNullOrEmpty()) {
            this.list.addAll(data)
        }
        notifyDataSetChanged()
    }

    class DataViewHolder(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    ) {

        var binding: ItemDetailWeatherBinding? = null

        fun bind() {
            if (binding == null) {
                binding = DataBindingUtil.bind(itemView)
            }
        }

        fun unbind() {
            binding?.unbind()
        }

        fun setViewModel(viewModel: ItemDetailWeatherVm?) {
            if (binding != null) {
                binding!!.vm = viewModel
            }
        }

        init {
            bind()
        }
    }


}