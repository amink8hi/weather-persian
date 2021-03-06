package com.hanamin.weather.widget

import android.content.Context
import android.view.View
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.listener.GuideListener

class ShowCaseView(private val context: Context) {

    private val builder = GuideView.Builder(context)

    fun createShowCase(view: View, contentText: String, onClick: () -> Unit) {
        builder.setContentText(contentText)
            .setTargetView(view)
            .setDismissType(DismissType.outside) //optional - default dismissible by TargetView
            .setGuideListener(GuideListener {
                onClick.invoke()

            })
            .build()
            .show()
    }


}