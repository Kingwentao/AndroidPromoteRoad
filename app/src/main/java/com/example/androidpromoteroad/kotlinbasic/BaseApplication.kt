package com.example.androidpromoteroad.kotlinbasic

import android.app.Application
import android.content.Context

/**
 * author: created by wentaoKing
 * date: created in 2020-07-19
 * description:
 */
class BaseApplication: Application() {

    companion object{
        private lateinit var mApplicationContext: Context

        fun currentApplication(): Context{
            return mApplicationContext
        }

        @JvmStatic
        fun currentApplication2(): Context{
            return mApplicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        mApplicationContext = this
    }
}