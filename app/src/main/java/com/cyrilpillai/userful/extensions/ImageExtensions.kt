package com.cyrilpillai.userful.extensions

import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.cyrilpillai.userful.R
import com.cyrilpillai.userful.utils.CircleTransform
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

fun ImageView.setRemoteImage(
        imageUrl: String = "",
        @DrawableRes placeholderDrawable: Int = R.drawable.placeholder_gradient_rectangle,
        @DrawableRes errorDrawable: Int = R.drawable.placeholder_gradient_rectangle,
        transformation: Transformation? = null): ImageView {
    if (imageUrl.isNotBlank()) {
        val requestCreator = Picasso.with(context).load(imageUrl)
                .error(errorDrawable)
                .placeholder(placeholderDrawable)
                .fit()
        transformation?.let { requestCreator.transform(transformation) }
        requestCreator.into(this@setRemoteImage)
    } else {
        setImageResource(placeholderDrawable)
    }
    return this@setRemoteImage
}