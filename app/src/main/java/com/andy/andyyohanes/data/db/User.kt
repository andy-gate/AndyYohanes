package com.andy.andyyohanes.data.db

import android.widget.ImageView
import androidx.room.Entity
import com.bumptech.glide.Glide

@Entity
data class User(
    var id : Int? = null,
    var login: String? = null,
    var avatar_url: String? = null
)