package com.hanamin.weather.ui.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.hanamin.weather.R
import com.hanamin.weather.data.db.room.RoomDataBase
import com.hanamin.weather.data.local.CityListModel
import com.hanamin.weather.databinding.ItemListCityBinding
import com.hanamin.weather.network.api.NetworkApi
import com.hanamin.weather.ui.view.customs.KitToast
import com.hanamin.weather.ui.viewmodel.ItemListVm
import com.hanamin.weather.utils.FileUtils

class ListCityAdapter(
    private val list: MutableList<CityListModel?>,
    private val networkApi: NetworkApi,
    private val loading: MutableLiveData<Boolean>,
    private val kitToast: KitToast,
    private val cityListDataBase: RoomDataBase,
    private val fileUtils: FileUtils
) :
    RecyclerView.Adapter<ListCityAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_city,
            FrameLayout(parent.context), false
        )
        return DataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val dataModel: CityListModel? = list[position]
        holder.setViewModel(
            ItemListVm(
                dataModel,
                networkApi,
                loading,
                kitToast,
                cityListDataBase,
                fileUtils
            )
        )


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

    fun updateData(data: MutableList<CityListModel?>) {
        if (!data.isNullOrEmpty()) {
            this.list.addAll(data)
        }
        notifyDataSetChanged()
    }

    class DataViewHolder(itemView: View?) : RecyclerView.ViewHolder(
        itemView!!
    ) {

        var binding: ItemListCityBinding? = null

        fun bind() {
            if (binding == null) {
                binding = DataBindingUtil.bind(itemView)
            }
        }

        fun unbind() {
            binding?.unbind()
        }

        fun setViewModel(viewModel: ItemListVm?) {
            if (binding != null) {
                binding!!.vm = viewModel
            }
        }

        init {
            bind()
        }
    }


}