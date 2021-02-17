package com.hanamin.weather.ui.view.customs

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatTextView
import com.hanamin.weather.R


class CustomTextView : AppCompatTextView {
    constructor(context: Context?) : super(context!!)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initAttrs(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initAttrs(context, attrs)
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        if (attrs != null) {
            val attributeArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.CustomTextView
            )
            var drawableLeft: Drawable? = null
            var drawableRight: Drawable? = null
            var drawableBottom: Drawable? = null
            var drawableTop: Drawable? = null
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                drawableLeft =
                    attributeArray.getDrawable(R.styleable.CustomTextView_drawableLeftCompatTv)
                drawableRight =
                    attributeArray.getDrawable(R.styleable.CustomTextView_drawableRightCompatTv)
                drawableBottom =
                    attributeArray.getDrawable(R.styleable.CustomTextView_drawableBottomCompatTv)
                drawableTop =
                    attributeArray.getDrawable(R.styleable.CustomTextView_drawableTopCompatTv)
            } else {
                val drawableLeftId = attributeArray.getResourceId(
                    R.styleable.CustomTextView_drawableLeftCompatTv,
                    -1
                )
                val drawableRightId = attributeArray.getResourceId(
                    R.styleable.CustomTextView_drawableRightCompatTv,
                    -1
                )
                val drawableBottomId = attributeArray.getResourceId(
                    R.styleable.CustomTextView_drawableBottomCompatTv,
                    -1
                )
                val drawableTopId =
                    attributeArray.getResourceId(R.styleable.CustomTextView_drawableTopCompatTv, -1)
                val minusOne = -1
                if (drawableLeftId != minusOne) drawableLeft =
                    AppCompatResources.getDrawable(context, drawableLeftId)
                if (drawableRightId != minusOne) drawableRight =
                    AppCompatResources.getDrawable(context, drawableRightId)
                if (drawableBottomId != minusOne) drawableBottom =
                    AppCompatResources.getDrawable(context, drawableBottomId)
                if (drawableTopId != minusOne) drawableTop =
                    AppCompatResources.getDrawable(context, drawableTopId)
            }
            setCompoundDrawablesWithIntrinsicBounds(
                drawableLeft,
                drawableTop,
                drawableRight,
                drawableBottom
            )
            attributeArray.recycle()
        }
    }
}