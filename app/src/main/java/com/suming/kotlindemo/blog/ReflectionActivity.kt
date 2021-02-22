package com.suming.kotlindemo.blog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlin.reflect.*
import kotlin.reflect.full.callSuspend
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.reflect
import kotlin.reflect.KMutableProperty0
import kotlin.reflect.KProperty1
import kotlin.reflect.full.valueParameters

/**
 * @创建者 mingyan.su
 * @创建时间 2021/02/03 16:23
 * @类描述 {TODO}反射
 * 需要添加 kotlin-reflect.jar
 */
var number = 0

class ReflectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * 1.类的引用
         */
        //kotlin 类 ReflectionActivity 的引用
        val ktRef: KClass<ReflectionActivity> = ReflectionActivity::class
        //java 类 ReflectionActivity 的引用
        val javaRef: Class<ReflectionActivity> = ReflectionActivity::class.java//将给定的KClass实例转化为相对应的Java Class实例

        /**
         * 2.KClass
         * Kotlin 反射的 API 的主要入口是 KClass，它代表一个类，对应的是 Java 中的 java.lang.reflect.Class，
         * 可以用它列举和访问类中包含的所有声明和它的超类中的声明。
         */
        val person = Person("Kotlin", 5)
        val kclass = person.javaClass.kotlin//返回一个KClass<Person>的实例

        println("kclass == ${kclass.simpleName}")//类的名称
        kclass.memberProperties.forEach {//遍历出所有属性名称
            println("遍历属性名 == ${it.name}")
        }

        /**
         * 3.KCallable
         * 由类的所有成员组成的列表是一个 KCallable 实例的集合。
         */
        val kFunction: KFunction1<Int, Unit> = ::shareInt
        kFunction.invoke(10)
//        kFunction.call(42)

        /**
         * 4.KFunction
         * 如果你有一个具体类型的 KFunction，它的形参类型和返回类型是确定的，那么优先使用这个具体类型的 invoke 方法，
         * call 方法是对所有类型都有效的通用手段，但是它不提供类型安全性。
         */
        val sumKF: KFunction2<Int, Int, Int> = ::sum
        println(sumKF.invoke(1, 2) + sumKF(3, 4))//打印10

        /**
         * 5.KProperty
         * 可以在一个 KProperty 实例上调用 call 方法，它会调用该属性的 getter。但是属性接口会为你提供一个更好获取属性值的方式：get 方法。
         */
        var kProperty: KMutableProperty0<Int> = ::number
        kProperty.setter.call(123)//通过反射调用setter，把123作为
        // 实参传递
        println(kProperty.get())//通过get获取属性的值，打印123

        val memberProperty: KProperty1<Person, Int> = Person::age
        println(memberProperty.get(person))//打印：5

        val s = ::ReflectionActivity
    }

    fun shareInt(number: Int) = println(number)

    fun sum(x: Int, y: Int) = x + y

    class Person(val name: String, val age: Int)

}