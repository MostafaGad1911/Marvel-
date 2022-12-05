package mostafagad.projects.marvelcharacters.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import mostafagad.projects.marvelcharacters.R

object Ext {

    fun String.loadImg(img: ImageView, ctx: Context) {
        Glide.with(ctx).load(this).placeholder(R.mipmap.ic_launcher)
            .into(img)
    }

    fun String.toast(ctx: Context) =
        Toast.makeText(ctx, this, Toast.LENGTH_LONG).show()

    fun View.show() {
        this.visibility = View.VISIBLE
    }

    fun View.hide() {
        this.visibility = View.GONE
    }

}
