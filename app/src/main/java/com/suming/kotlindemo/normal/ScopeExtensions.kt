package com.suming.kotlindemo.normal

import com.suming.kotlindemo.User
import com.suming.kotlindemo.blog.SealedClassActivity

/**
 * @创建者 mingyan.su
 * @创建时间 2020/08/17 17:11
 * @类描述 {$TODO} 不同包下的拓展函数
 */

fun User.share() {
    //TODO
}

//子类的类的拓展
fun SealedClassActivity.BaseSealed.Student.share(name: String): String {
    return name
}

class ScopeExtensions {
}