package com.louis.bangkitbfaasubmission

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadCircleImage(src: Int){
    Glide.with(this.context)
        .load(src)
        .circleCrop()
        .into(this)
}