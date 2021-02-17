package com.hanamin.weather.ui.view.customs

import android.content.Context
import android.os.Handler
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.hanamin.weather.R
import dagger.hilt.android.scopes.FragmentScoped


@FragmentScoped
class KitToast constructor(private val context: Context) {

    private val mHandler = Handler()
    fun successToast(text: String = "", @StringRes id: Int = 0) {
        val layout = LayoutInflater.from(context).inflate(R.layout.toast, null, false)
        val tv = layout.findViewById<TextView>(R.id.toast_text)
        tv.text = getText(text, id)
        tv.background =
            ContextCompat.getDrawable(context, R.drawable.round_toast_background_success)
        tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_white_24dp, 0)
        tv.gravity = Gravity.CENTER
        tv.compoundDrawablePadding = 16
        toastIt(layout)
    }

    fun errorToast(text: String = "", @StringRes id: Int = 0) {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        layoutInflater?.let {
            val layout = layoutInflater.inflate(R.layout.toast, null)
            val tv = layout.findViewById<TextView>(R.id.toast_text)
            tv.text = getText(text, id)
            tv.background =
                ContextCompat.getDrawable(context, R.drawable.round_toast_background_error)
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_white_24dp, 0)
            tv.gravity = Gravity.CENTER
            tv.compoundDrawablePadding = 16

            toastIt(layout)
        }
    }

    fun warningToast(text: String = "", @StringRes id: Int = 0) {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        layoutInflater?.let {
            val layout = layoutInflater.inflate(R.layout.toast, null)
            val tv = layout.findViewById<TextView>(R.id.toast_text)
            tv.text = getText(text, id)
            tv.background =
                ContextCompat.getDrawable(context, R.drawable.round_toast_background_warning)
            tv.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_warning_white_24dp, 0)
            tv.gravity = Gravity.CENTER
            tv.compoundDrawablePadding = 16

            toastIt(layout)
        }
    }

    fun normalToast(text: String = "", @StringRes id: Int = 0) {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        layoutInflater?.let {
            val layout = layoutInflater.inflate(R.layout.toast, null)
            val tv = layout.findViewById<TextView>(R.id.toast_text)
            tv.text = getText(text, id)
            tv.background =
                ContextCompat.getDrawable(context, R.drawable.round_toast_background_normal)
            tv.gravity = Gravity.CENTER
            tv.compoundDrawablePadding = 16
            toastIt(layout)
        }
    }

    private fun toastIt(layout: View) {
        val toast = Toast(context)
        toast.duration = Toast.LENGTH_SHORT
        toast.view = layout
        toast.show()
        mHandler.postDelayed({ toast.cancel() }, 2000)
    }

    private fun getText(text: String, @StringRes id: Int): String = if (id != 0) {
        context.resources.getString(id)
    } else {
        text
    }
}
