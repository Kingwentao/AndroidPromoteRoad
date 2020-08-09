package com.example.androidpromoteroad.architecture.mvvm

import android.widget.EditText
import androidx.core.widget.doAfterTextChanged

/**
 * author: created by wentaoKing
 * date: created in 2020/8/9
 * description: 绑定数据和view的管理类
 */
class ViewBinder {

    companion object {

        /**
         * 控件和数据对象的双向绑定
         */
        fun bindViewAndData(editText: EditText, stringAttr: StringAttr) {
            editText.doAfterTextChanged {
                if (stringAttr.value != it.toString()) {
                    println("通知内存对象改变 ${it.toString()}")
                    stringAttr.value = it.toString()
                }

            }
            stringAttr.onValueChangeListener = object : StringAttr.OnValueChangeListener {
                override fun onValueChanged(newValue: String?) {
                    if (newValue != null && newValue != editText.text.toString()) {
                        println("通知表现对象改变 ${newValue}")
                        editText.setText(newValue)
                    }
                }
            }
        }
    }
}