package com.suming.kotlindemo.blog

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * @创建者 mingyan.su
 * @创建时间 2020/07/24 15:28
 * @类描述 {$TODO}函数的使用
 */
open class FunctionActivity : AppCompatActivity() {
    private val TAG = "EmptyJudgmentActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //成员函数直接调用
        basic()
        var str = basic2()//有返回值

        //通过对象调用，先初始化对象
        FunctionActivity().basic()
        var str2 = FunctionActivity().basic2()//有返回值

        Log.e(TAG, "带参数的函数：sum == ${sum(1, 2)}")

        //默认参数使用
        defaultArgs()
        defaultArgs(null, 20f)

        //复写的默认参数
        B().defaultArgsOver(1)

        //命名参数
        nameArgs(str = "HelloWord", float = 10f)

        //可变参数
        varargArgs("1", "2", "3", "4")

        //单表达式函数
        Log.e(TAG, "单表达式函数：sum == ${singleExpression(10)}")
        Log.e(TAG, "单表达式函数：sum == ${singleExpression2(4, 5)}")
    }

    //1.Kotlin中函数的声明关键字为`fun`，默认为`public`可见修饰符，小括号`()`是必须存在的，即使没有参数的情况，
    // 大括号`{}`也是必须存在，即使没有函数体。如果函数没有返回值则可以省略返回值类型。
    fun basic() {

    }

    fun basic2(): String {
        return ""
    }

    //2.定义一个有参数的函数，使用`Pascal`法表示定义，即：`name : type`。其中参数是必须有显示的参数类型，并且参数与参数之间用`,`隔开。
    //参数numA，numB是Int类型，返回值是Int类型
    fun sum(numA: Int, numB: Int): Int {
        return numA + numB
    }

    //3.默认参数  如果函数中参数有默认值，可以对有默认值的参数不传递参数值，会使用默认值。
    fun defaultArgs(argsA: Int? = 0, argsB: Float = 1f, argsC: Boolean = false) {
        Log.e(TAG, "默认参数：argsA == $argsA | argsB == $argsB | argsC == $argsC")
    }

    open fun defaultArgsOver(int: Int = 10) {}

    /**
     * 4.当该函数是一个成员函数，并且该函数复写继承类中的方法，则该函数必须从签名中省略该函数的默认值，
     * 在你复写方法时，编辑器默认帮你实现。
     */
    class B : FunctionActivity() {
        //复写方法
        override fun defaultArgsOver(a: Int) {
            super.defaultArgsOver(a)
            Log.e("EmptyJudgmentActivity", "默认参数：argsOver == $a")
        }
    }

    //5.命名参数：命名参数是指使用函数传递参数时，参数传入的格式是`参数名 = 参数值`这种形式传递的。
    // 在函数有个多个参数(有默认值)时，如果只需要传一部分参数，而其他参数使用默认值时，那么我们只需要指定命名参数就可以了。
    fun nameArgs(str: String, num: Int = 0, float: Float = 1.0f, isTrue: Boolean = false) {
        Log.e(TAG, "命名参数：str == $str | num == $num  | float == $float  | isTrue == $isTrue")
    }

    //6.可变参数：函数中的可变长参数使用`vararg`修饰，即当函数中的参数是不定个数并且是同一类型，
    // 则可以使用`vararg`修饰这个参数，被它修饰的参数相当于一个固定类型的数组。
    fun varargArgs(vararg strs: String) {
        for (item in strs) {
            Log.e(TAG, "可变参数：strs == $item")
        }
    }

    //7.无返回值,Unit修饰，此类型对应于Java中的“void”类型。
    fun unitReturn(): Unit {
        return
    }

    //无返回值
    fun unitReturn2() {

    }

    //8.其他类型的返回值说明该类型有返回值，并且返回值类型不能省略，返回值也不能省略。
    //返回值为String类型
    fun otherReturn(): String {
        return ""
    }

    //返回值为Int类型
    fun otherReturn2(): Int {
        return 0
    }

    //9.单表达式函数：单表达式函数表示在函数具备返回值时，可以省略花括号`{}`直接用` = `赋值指定的代码体，
    // 而函数的返回值是有编译器自动推算的。
    fun singleExpression(num: Int) = num * 10

    fun singleExpression2(x: Int, y: Int): Int = x * y
}