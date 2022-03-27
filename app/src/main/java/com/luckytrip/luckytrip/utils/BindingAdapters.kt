package com.luckytrip.luckytrip.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.luckytrip.luckytrip.R

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("loadImage")
    fun ImageView.loadImage(imageLink: String) {
        Glide.with(this.context).load(imageLink).placeholder(R.drawable.ic_image_place_holder).error(R.drawable.ic_image_place_holder).into(this)
    }

    @JvmStatic
    @BindingAdapter("handleVisibility")
    fun View.handleVisibility(isVisible: Boolean) {
        this.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("setOnClick")
    fun View?.setOnClickListener(listener: View.OnClickListener?) {
        this?.setOnClickListener {
            listener?.onClick(this)
        }

    }
}