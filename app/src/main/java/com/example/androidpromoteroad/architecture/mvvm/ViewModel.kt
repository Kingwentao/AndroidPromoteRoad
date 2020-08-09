package com.example.androidpromoteroad.architecture.mvvm

import android.widget.EditText

/**
 * author: created by wentaoKing
 * date: created in 2020/8/9
 * description: mvvm 中的 vm 层
 */
class ViewModel(dataView1: EditText) {

    var dataStringAttr: StringAttr = StringAttr()

    init {
        ViewBinder.bindViewAndData(dataView1, dataStringAttr)
    }

    fun init() {
        dataStringAttr.value = DataCenter.getData()
    }
}