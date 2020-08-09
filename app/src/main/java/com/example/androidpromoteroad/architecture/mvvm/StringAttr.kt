package com.example.androidpromoteroad.architecture.mvvm

/**
 * author: created by wentaoKing
 * date: created in 2020/8/9
 * description: 被绑定的对象数据
 */
class StringAttr {
    var value: String? = null
        set(value) {
            field = value
            onValueChangeListener?.onValueChanged(value)
        }

    var onValueChangeListener: OnValueChangeListener? = null

    interface OnValueChangeListener {
        fun onValueChanged(newValue: String?)
    }

}