package com.andy.andyyohanes.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
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

fun Context.hideKeyboard(activity: Activity?) {
    if (activity == null) return
    val view = activity.currentFocus
    if (view != null) {
        val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    if(!url.isNullOrEmpty())
        Glide.with(view.context).load(url).into(view)
}