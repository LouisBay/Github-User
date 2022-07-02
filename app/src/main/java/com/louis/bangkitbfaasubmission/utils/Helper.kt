package com.louis.bangkitbfaasubmission.utils

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.louis.bangkitbfaasubmission.R

object Helper {
    fun ImageView.loadCircleImage(src: String){
        Glide.with(this.context)
            .load(src)
            .placeholder(R.drawable.default_avatar)
            .error(R.drawable.default_avatar)
            .circleCrop()
            .into(this)
    }

    fun String?.checkString() = if (this.isNullOrBlank()) "-" else this

    fun showError(context: Context, errorMessage: String) {
        Toast.makeText(context, "An error occured : $errorMessage", Toast.LENGTH_SHORT).show()
    }
}


