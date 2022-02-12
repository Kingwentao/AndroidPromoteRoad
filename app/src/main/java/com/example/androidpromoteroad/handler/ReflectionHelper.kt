package com.example.androidpromoteroad.handler

import android.util.Log
import java.lang.IllegalArgumentException
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.Field
import java.lang.reflect.Modifier


object ReflectionHelper {

    private const val TAG = "ReflectionHelper"

    @kotlin.jvm.Throws(InvocationTargetException::class)
    fun invokeMethod(
        any: Any, methodName: String, parameterTypes: Array<Class<*>?>, parameters: Array<Any?>
    ): Any? {
        val method: Method = getDeclaredMethod(any, methodName, parameterTypes)
            ?: throw IllegalArgumentException("Could not find method [$methodName] on target [$any]")
        method.isAccessible = true
        try {
            return method.invoke(any, parameters)
        } catch (e: IllegalAccessException) {
            Log.d(TAG, "invokeMethod: ")
        }
        return null
    }

    fun getDeclaredField(any: Any, filedName: String): Field? {
        var superClass: Class<*>? = any.javaClass
        while (superClass != Any::class.java) {
            try {
                return superClass!!.getDeclaredField(filedName)
            } catch (e: NoSuchFieldException) {
                //Field 不在当前类定义, 继续向上转型
            }
            superClass = superClass!!.superclass
        }
        return null
    }

    fun getDeclaredMethod(
        any: Any,
        methodName: String,
        parameterTypes: Array<Class<*>?>
    ): Method? {
        var superClass: Class<*>? = any.javaClass
        while (superClass != Any::class.java) {
            try {
                return superClass!!.getDeclaredMethod(methodName, *parameterTypes)
            } catch (e: NoSuchMethodException) {
                //Method 不在当前类定义, 继续向上转型
            }
            superClass = superClass!!.superclass
        }
        return null
    }

    fun setFieldValue(any: Any, fieldName: String, value: Any) {
        val field = getDeclaredField(any, fieldName)
            ?: throw IllegalArgumentException("Could not find field [$fieldName] on target [$any]")
        makeAccessible(field)
        try {
            field[any] = value
        } catch (e: IllegalAccessException) {
            Log.e(TAG, "setFieldValue: ", e)
        }
    }

    fun makeAccessible(field: Field) {
        if (!Modifier.isPublic(field.modifiers)) {
            field.isAccessible = true
        }
    }

}
