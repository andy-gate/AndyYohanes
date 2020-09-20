package com.andy.andyyohanes.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun ProgressBar.show(){
    visibility = View.VISIBLE
}

fun ProgressBar.hide(){
    visibility = View.GONE
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    if(!url.isNullOrEmpty())
        Glide.with(view.context).load(url).into(view)
}