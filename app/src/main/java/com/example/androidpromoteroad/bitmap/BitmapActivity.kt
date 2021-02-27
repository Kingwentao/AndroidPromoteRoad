package com.example.androidpromoteroad.bitmap

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Binder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.BundleCompat.getBinder
import com.example.androidpromoteroad.R
import com.example.androidpromoteroad.drawview.DrawViewActivity

class BitmapActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bitmap)
    }

    //bitmap复用方法
    fun bitmapReuse() {
        val oldOption = BitmapFactory.Options()
        oldOption.inMutable = true
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar, oldOption)

        val newOption = BitmapFactory.Options()
        newOption.inMutable = true
        newOption.inBitmap = bitmap
        val newBitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar, newOption)
    }

    //发送端：传输大图片
    fun sendBigBitmap() {
        val intent = Intent()
        val bundle = Bundle()
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.avatar)
        bundle.putBinder("bitmap", ImageBinder(bitmap))
        intent.putExtras(bundle)
        startActivity(intent)
    }

    class ImageBinder(private val bitmap: Bitmap) : Binder() {
        fun getBitmap(): Bitmap {
            return bitmap
        }
    }

    //接收端：接受大图片
    fun receiveBigBitmap() {
        val bundle = intent.extras
        bundle?.let {
            val imageBinder = getBinder(bundle, "bitmap") as ImageBinder
            val bitmap = imageBinder.getBitmap()
        }
    }

}