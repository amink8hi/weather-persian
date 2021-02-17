package com.hanamin.weather.utils

import android.content.Context
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hanamin.weather.ui.view.customs.RtlGridLayoutManager
import com.hanamin.weather.ui.view.customs.WrapContentLinearLayoutManager

class RecyclerViewTools {

    fun isLastItemDisplaying(recyclerView: androidx.recyclerview.widget.RecyclerView): Boolean {
        if (recyclerView.adapter?.itemCount != 0) {
            val lastVisibleItemPosition =
                (recyclerView.layoutManager as WrapContentLinearLayoutManager).findLastCompletelyVisibleItemPosition()
            return lastVisibleItemPosition != androidx.recyclerview.widget.RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.adapter!!.itemCount - 1
        }
        return false
    }

    fun isLastItemDisplayingGridLayout(recyclerView: androidx.recyclerview.widget.RecyclerView): Boolean {
        if (recyclerView.adapter?.itemCount != 0) {
            val lastVisibleItemPosition =
                (recyclerView.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
            return lastVisibleItemPosition != androidx.recyclerview.widget.RecyclerView.NO_POSITION && lastVisibleItemPosition == recyclerView.adapter!!.itemCount - 1
        }
        return false
    }

    fun initRecyclerViewRtlGrid(context: Context, recyclerView: RecyclerView, spanCount: Int) {
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()
        var gridLayoutManager = RtlGridLayoutManager(context, spanCount)
        recyclerView.layoutManager = gridLayoutManager
    }

    fun initRecyclerViewLinear(
        context: Context,
        recyclerView: RecyclerView,
        orientation: Int,
        reverseLayout: Boolean
    ) {
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context, orientation, reverseLayout)
        recyclerView.layoutManager = layoutManager
    }

}
