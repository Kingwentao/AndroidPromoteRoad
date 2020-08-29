package com.example.androidpromoteroad.annotationprocess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidpromoteroad.R

/**
 * 注解处理
 */
class AnnotationProcessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annotation_process)
        testDeprecated()
    }

    /**
     * replace {@link #n}
     */
    @Deprecated("")
    fun testDeprecated(){

    }

    fun newFunction(){

    }
}