package com.example.androidpromoteroad.kotlinupgrade

/**
 * author: WentaoKing
 * created on: 2020/7/22
 * description:
 */
val user = User("a","b")
val copyUser = user.copy()

fun main() {
    println(copyUser == user)
    println(copyUser === user)
}