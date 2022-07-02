package com.louis.bangkitbfaasubmission

import android.widget.ImageView
import com.bumptech.glide.Glide

object Api {
    const val API_TOKEN = BuildConfig.GITHUB_API_TOKEN
}

fun ImageView.loadCircleImage(src: String){
    Glide.with(this.context)
        .load(src)
        .placeholder(R.drawable.default_avatar)
        .error(R.drawable.default_avatar)
        .circleCrop()
        .into(this)
}

fun String?.checkString() = if (this.isNullOrBlank()) "-" else this

