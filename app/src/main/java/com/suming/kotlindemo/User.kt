package com.suming.kotlindemo

/**
 * @创建者 mingyan.su
 * @创建时间 2020/06/09 16:58
 * @类描述
 */
data class User(var name: String, var age: Int, var sex: String) {
    var info: Info? = null
}