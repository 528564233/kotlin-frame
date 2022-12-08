package com.base.app.adapter

import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions


class BindAdapter {
    companion object {
        /**
         *url 图片的地址
         *
         */
        @BindingAdapter("android:background")
        @JvmStatic
        fun setImage(iv: View, placeHolder: Drawable) {
            iv.background = placeHolder
        }

        @BindingAdapter("imageUrl")
        @JvmStatic
        fun loadImage(view: ImageView, url: String) {
            Glide.with(view.context).load(url).apply(RequestOptions.bitmapTransform(CircleCrop()))
                .into(view)
        }

        @BindingAdapter("srcImg")
        @JvmStatic
        fun setImgBg(view: ImageView, url: Int) {
            view.setImageResource(url)
        }

        @BindingAdapter("hint")
        @JvmStatic
        fun setHint(view: TextView, url: Int) {
            view.setHint(url)
        }

        @BindingAdapter("anyText")
        @JvmStatic
        fun setTextAll(view: TextView, a: Any) {
            view.text = "" + a
        }

        @BindingAdapter("text")
        @JvmStatic
        fun setText(view: TextView, a: Int) {
            view.setText(a)
        }


        @BindingAdapter("visibility")
        @JvmStatic
        fun setVisibility(view: View, b: Int) {
            view.visibility =
                if (b == 0) View.VISIBLE else if (b == 2) View.GONE else View.INVISIBLE
        }

    }
}