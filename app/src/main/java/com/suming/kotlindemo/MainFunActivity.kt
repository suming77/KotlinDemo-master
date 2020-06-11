package com.suming.kotlindemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

//kotlin的基础语法
class MainFunActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //1.kotlin文件一般以.kt为后缀
        //Kotlin源文件不需要相匹配的目录和包，源文件可以放在任何文件目录
        str()

        rangeTo()
    }

    fun main(args: Array<String>) {
        str()
    }

    //2.函数的定义
    //使用关键字fun，参数格式为：参数:类型
    fun sum(a: Int, b: Int): Int {//sum:方法名，a、b表示参数，Int表示参数类型，返回值是Int
        return a + b
    }

    //表达式作为函数体，返回类型自动判断
    fun sum2(a: Int, b: Int) = a + b

    public fun sum3(a: Int, b: Int): Int = a + b//public方法必须明确写出返回值类型


    //无返回值的函数(不管有没有public声明，都可以省略的)
    fun printSum(a: Int, b: Int): Unit {
        print(a + b)
    }

    public fun printSum2(a: Int, b: Int) {
        print(a + b)
    }

    //2.可变长参数函数,调用vars(1,2,3,5,9),输出12359
    fun vars(vararg v: Int) {
        for (vt in v) {
            print(vt)
        }
    }

    //3.定义长量和变量
    fun varAndVal() {

        //3.1)可变变量定义：var 关键字
        //var <标识符> : <类型> = <初始化值>
        var a: Int = 1//变量a,Int类型，初始化值为1

        //3.2)常量定义，val关键字，只能赋值一次的变量(类似Java中final的修饰)
        val b: String = "";

        //常量和变量都可以没有初始化值，但在引用前必须初始化(方法内，方法外仍需初始化)
        //编译器支持自动类型判断，即声明时可以不用指定类型，有编译器判断
        var c: Int = 1
        val d: Int  //如果没有初始化值，则需要声明类型
        d = 1       //明确赋值
        val e = ""  //系统自动识别

        var f = 2 //系统自动判断为Int类型，变量可修改
        f = 5
    }

    //4.字符串模板
    //与Java不同，kotlin的块注释允许嵌套
    //$表示变量名或者变量值
    //$varName表示变量值
    //${varName.fun()}表示变量方法返回值
    fun str() {
        var a = 1
        val b = "a is $a"

        a = 2;
        val c = "${b.replace("is", "was")},but now is $a"

        print("b:" + b + ",c:" + c)
    }


    //5.null检查机制
    //Kotlin 的空安全设计对于可为空的参数，在使用时要进行空判断处理
    //有两种处理方式：1.字段后面加!! 像Java那样抛出空指针异常；2.字段后加?可不做处理或者配合?:做空判断处理

    fun Null() {
        //类型后面加?表示可以为null
        var a: String? = "23"

        //表示不做处理，可以返回null
        val b = a?.toInt()

        //表示抛出空指针异常
        val c = a!!.toInt()

        //表示如果为空则返回-1
        val d = a?.toInt() ?: -1


        //当一个引用可能为null的时候，对应的类型必须明确标记可为null
        //当str中的字符串内容不是一个整数的时候，返回null
        fun praseInt(str: String): Int? {
            return 1
        }

        //演示一个返回值可为null的函数
        fun main(args: Array<String>) {
            if (args.size < 2) {
                return
            }

            val a = praseInt(args[0])
            val b = praseInt(args[1])

            //需要判断，不能直接使用，有可能为null
            if (a != null && b != null) {
                //在进行过null值检查过后，x和y的类型会被自动专为非null变量
                print("a*b:" + a * b)
            }
        }
    }

    //6.类型检测以及自动类型转换
    //我们使用is 运算符检测一个表达式是否为某一类型的一个实例(类似Java中的instance of)
    fun checkType() {
        fun getStringLength(obj: Any): Int? {
            if (obj is String) {
                //做过类型判断后，obj会被系统自动转为String类型
                return obj.length
            }
            //这里的obj仍然是Any类型的引用
            return null

            //这里还有一种用法，与instance of不同，用!is
//            if (obj !is String){
//
//            }
        }

        fun getStringLength2(obj: Any): Int? {
            if (obj !is String) {
                return null
            }
            //这个分支中，obj的类型会自动转为String
            return obj.length
        }

        fun getStringLength3(obj: Any): Int? {
            //在“&&”运算的右侧，obj类型会被自动转为String
            if (obj is String && obj.length > 0) {
                return obj.length
            }
            return null
        }
    }

    //7.区间
    //区间表达式由具有具有操作符形式的..的rangTo函数辅以in和!in形成
    //区间是为任何可比较类型定义的，但对于整形的原生类型，有一个优化的实现
    fun rangeTo() {
        for (i in 1..4) {
            print(i)//1234
        }

        for (i in 4..1) {
            print(i)//什么也不打印
        }


        for (i in 1..10) {
            print(i)//等同于1<=i && i>=10
        }

        for (i in 1..4 step 2) {
            print(i)//使用step指定步长，输出13
        }

        for (i in 4 downTo 1 step 2) {
            print(i)//输出42
        }

        //使用until排除函数结束元素
        for (i in 1 until 10) {
            print(i)//i in[1,10) 排除了10
        }
    }

}
