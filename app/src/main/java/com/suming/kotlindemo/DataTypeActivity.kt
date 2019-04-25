package com.suming.kotlindemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

//基本数据类型
class DataTypeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_type)
    }

    fun valueType() {
        //1.kotlin的基本数值类型包括Byte 8 Short 16 Int 32 Long 64 Float 32 Double 64
        //字符不是数值类型，是一个独立的数据类型

        //2.字面常量
        //十进制：123
        //长整形以大写的L结尾：123L
        //16进制以0x开头：0x0F
        //2进制以0b开头：0b00100011
        //不支持8进制

        //kotlin支持传统符号表示的浮点数值
        //Doubles 默认写法：12.4,123.5e10
        //Floats使用f或者F后缀：123.5f

        //可以使用下划线使用数字更易读
        val oneMillion = 1_000_000
        val bytes = 0b01001101_00110101
        val hexByte = 0xFF_EC_5E
        val socialSecurityNumber = 999_99_999L
        val creditCardNumber = 1234_4353_2354_2342L
    }

    //3.比较数字
    fun compareValue() {
        //Kotlin中没有基础数据类型，只有封装的数字类型，你没封装一个变量，kotlin帮你封装了一个对象，
        // 这样可以保证不出现空指针异常，数字类型也一样，比较大小时就有比较数值大小和对象地址比较
        //在kotlin中，三个等号===比较比较对象，两个等号==表示比较值大小
        val a: Int = 1;
        print(a === a)//值大小相等，对象地址相等

        //经过装箱，封装了两个对象
        val boxedA: Int = a;
        val anotherBoxedA: Int = a;

        //虽然经过了装箱，但是值都是1
        print(boxedA == anotherBoxedA)//true,值相等
        print(boxedA === anotherBoxedA)//false,对象地址不一样
    }

    //4.类型转换
    fun typeTransfrom() {
        //由于不同的表示方式，较小的类型并不是较大的类型的子类型，较小类型不能隐式转换较大类型。
        //需要进行显示转换
        val a: Int = 1//ok，子面值是静态检测的
//        val b: Byte = a // 错误

        val c: Byte = a.toByte()// OK

        //每种数据类型都有一下方法，可以转化为其他类型
//        toByte(): Byte
//        toShort(): Short
//        toInt(): Int
//        toLong(): Long
//        toFloat(): Float
//        toDouble(): Double
//        toChar(): Char

        //有些情况也是可以使用自动类型转化的，前提是根据上下文推断出正确的数据类型，而且数学操作符会做相应的重载
        val i = 1L + 3//Long + Int = Long
    }
    

}
