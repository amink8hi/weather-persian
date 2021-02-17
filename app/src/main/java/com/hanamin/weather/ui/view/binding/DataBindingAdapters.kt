package com.hanamin.weather.ui.view.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

@BindingAdapter("listImage")
fun bindImage(imageView: ImageView, url: String?) {
    Glide
        .with(imageView.context)
        .load(url)
        .centerCrop()
        .apply(RequestOptions.bitmapTransform(RoundedCorners(12)))
        /*      .placeholder(R.drawable.place_holder)*/
        .into(imageView)
}

@BindingAdapter("app:autoStartMarquee")
fun setAutoStartMarquee(textView: TextView, autoStartMarquee: Boolean) {
    textView.isSelected = autoStartMarquee
}

@BindingAdapter("app:resLottie")
fun setResLottie(lottie: LottieAnimationView, res: Int) {
    if (res != 0) {
        lottie.setAnimation(res)
        lottie.playAnimation()
    }
}