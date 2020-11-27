package com.suming.kotlindemo.blog

class DelegatingCollection<T>(val innerList: Collection<T> = mutableListOf<T>()) : Collection<T> by innerList {
        override fun isEmpty(): Boolean {
            return innerList.isEmpty()
        }
    }