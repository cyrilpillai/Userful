package com.cyrilpillai.userful.extensions

import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.cyrilpillai.userful.R
import com.squareup.picasso.Picasso

fun ImageView.setRemoteImage(
        imageUrl: String = "",
        @DrawableRes placeholderDrawable: Int = R.drawable.placeholder_gradient,
        @DrawableRes errorDrawable: Int = R.drawable.placeholder_gradient): ImageView {
    if (imageUrl.isNotBlank()) {
        Picasso.with(context).load(imageUrl)
                .error(errorDrawable)
                .placeholder(placeholderDrawable)
    } else {
        setImageResource(placeholderDrawable)
    }
    return this@setRemoteImage
}