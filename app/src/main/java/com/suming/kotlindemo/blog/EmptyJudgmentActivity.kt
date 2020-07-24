package com.suming.kotlindemo.blog

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.suming.kotlindemo.User

/**
 * @创建者 mingyan.su
 * @创建时间 2020/07/22 17:17
 * @类描述 {$TODO}可空类型，空安全，非空断言，类型转换
 */
class EmptyJudgmentActivity : AppCompatActivity() {
    private val TAG = "EmptyJudgmentActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        emptyType()
//        judgeEmpty()
//        emptyMerge()
//        emptyAssert()
        safeConvert()
    }

    /**
     * 安全转换运算符：`as?`
     *
     * 在 Kotlin 中使用as进行类型的强制转换，as?表示类型的安全转换。如果as进行类型转换是不能正常转换的话
     * 会抛出类型转换异常ClassCastException，而as?则会返回 null，不会抛出异常。
     */
    private fun safeConvert() {
//        var num: Int? = "Kotlin" as Int//抛出类型转换异常

        var str: String? = null
        var num = str as? Int
        Log.e(TAG, "安全转换运算符：num == $num")

        str = "Android"
        num = str as? Int
        Log.e(TAG, "安全转换运算符：num == $num")
    }

    /**
     * 非空断言运算符：`!!`
     *
     *非空断言运算符用于断言一个可空各类型变量，如果该变量为`null`，则会抛出空指针异常`kotlin.KotlinNullPointerException`。
     *`!!`方便爱好抛出空指针异常的开发者使用，在一个可空变量后面加上`!!`，如果变量为`null`，运行时会抛出空指针异常。
     */
    private fun emptyAssert() {
        //1.变量不为`null`
        var name: String? = "HelloWord"
        Log.e(TAG, "非空断言运算符：length == ${name!!.length}")

        //2.变量为`null`
        name = null
        val user = User(name!!, 1, "性别")//运行异常,抛出空指针异常
        Log.e(TAG, "非空断言运算符：length == ${user?.name}")
    }

    /**
     * Elvis运算符(空值合并运算符)：?:
     *
     * ?:是一个二元运算符，作用是判断可空类型时空值合并，Kotlin 中不存在三目运算符，
     * 语法为：`可空类型数据?:空值合并到的数据`。
     * 当数据非空时，直接返回数据，当数据为空时，返回合并到的数据。利用该运算，能很好把可空类型转为非空类型。
     */
    private fun emptyMerge() {
        var length = 0
        var strA: String? = "HelloWord"

        length = if (strA != null) strA.length else -1
        Log.e(TAG, "空值合并运算符 strA不为null：length == $length")

        strA = null
        length = strA?.length ?: -1//如果strA?.length为null则返回-1，否则返回strA?.length
        Log.e(TAG, "空值合并运算符 strA为null：length == $length")
    }


    /**
     * 可空类型:`?`
     *
     * 因为类中的变量必须要初始化值
     * 如果没有声明类型，系统会自动识别你赋值的类型，如果赋值为null，则系统自动声明为Nothing?，该变量值只能为null，不能重新赋值为其他类型值
     * 如果声明类型，但是类型后没有‘?’，则表示该变量不可以为null；在使用时不需要做空判断
     * 如果声明类型，类型后加‘?’，表示该变量可为null，如:Int?，在使用时需要做空判断
     */
    private fun emptyType() {
        //1.不可空类型并且初始化值为null
        var numA = null //没有声明类型，只能为null，不能重新赋值其他类型值
        //numA = 10     报错
        //numA = "文字" 报错

        //2.不可空类型并且初始化值不为null
        var numB: Int = 15
        //numB = null  报错，类型没有‘?’，不能赋值为null
        numB = 0 //可以赋值Int类型的值
        if (numB != null) {//numB声明不为null，这个判断没有意义

        }

        //3.可空类型
        var numC: String? = null//该变量可以为String类型的值和 null
        numC = "HelloWord"
        if (numC != null) {//可空类型，使用时需要做空判断

        }

        //4.参数是否可空
        fun setType(type: Int?) {//表示参数type可以为空

        }

        fun setType(type: Int) {//表示参数type不可以为空

        }

        //5.返回值是否可空
        fun getName(): String? {//表示返回的值可以为空
            return null
        }

//    fun getName(): String {//表示返回的值不可以为空
//        return "名字"
//    }
    }

    /**
     * 安全调用运算符：`?.`
     *
     * 判断空类型，安全调用运算符用于可空类型变量安全调用非空类型的属性或者函数，而不会抛出空指针异常
     * ，当对象为null时，直接返回空值null，否则进行调用并返回结果。
     */
    private fun judgeEmpty() {
        var user: User? = null

        //1.if…else
        if (user == null) {

        } else {

        }

        //2. ?.判断空,如果可空类型变量为null时，返回null
        Log.e(TAG, "安全调用运算符：user == name:${user?.name} | age: ${user?.age}| sex: ${user?.sex}")

        //3.链式调用获取user里面的info的address
        //链式调用能大量避免空指针异常NullPointException，因为链式整只有一个为null，则整个链式返回值就为null
        var address = user?.info?.address
        Log.e(TAG, "安全调用运算符：address == $address")

        //4.使用let函数，避免写一些判断null的操作，当你需要定义一个变量在一个特定的作用域范围内，
        // let函数更适合，它实际是一个作用域函数。使用格式如下：
        user?.let {
            //it表示user
            var name = it.name
            var age = it.age
            var sex = it.sex
        }
    }


}