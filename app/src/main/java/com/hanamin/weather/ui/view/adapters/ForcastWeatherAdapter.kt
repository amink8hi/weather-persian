package com.hanamin.weather.ui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.hanamin.weather.R
import com.hanamin.weather.data.local.FiveListModel
import com.hanamin.weather.databinding.ItemForcastWeatherBinding
import com.hanamin.weather.ui.viewmodel.ItemForcastWeatherVm

class ForcastWeatherAdapter(private val list: MutableList<FiveListModel?>) :
    RecyclerView.Adapter<ForcastWeatherAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_forcast_weather,
            FrameLayout(parent.context), false
        )
        return DataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val dataModel: FiveListModel? = list[position]
        holder.setViewModel(ItemForcastWeatherVm(dataModel))
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

    fun updateData(data: MutableList<FiveListModel>) {
        if (!data.isNullOrEmpty()) {
            this.list.addAll(data)
        }
        notifyDataSetChanged()
    }

    class DataViewHolder(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    ) {

        var binding: ItemForcastWeatherBinding? = null

        fun bind() {
            if (binding == null) {
                binding = DataBindingUtil.bind(itemView)
            }
        }

        fun unbind() {
            binding?.unbind()
        }

        fun setViewModel(viewModel: ItemForcastWeatherVm?) {
            if (binding != null) {
                binding!!.vm = viewModel
            }
        }

        init {
            bind()
        }
    }


}