package com.suming.kotlindemo.blog

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.suming.kotlindemo.R

/**
 * @创建者 mingyan.su
 * @创建时间 2020/07/02 10:50
 * @类描述 {$TODO}Blog专题<一>：Kotlin基础语法
 */

//常量
const val constA: String = "顶层声明"

class BaseGrammarActivity : AppCompatActivity() {
    private val TAG = "BaseGrammarActivity"

    companion object {
        const val constC = "伴生对象中声明"
    }

    //1.类中声明成员变量
    var mVar_a: Int = 0
    val mVal_b: Int = 0

    init {//初始化
        mVar_a = 100
        //mVal_b = 101 为val的变量为不可变变量，即初始化后数值不能再变化

        Log.e(TAG, "类中声明成员变量：mVar_a == $mVar_a | mVal_b == $mVal_b")
    }

    //2.类中声明可空变量
    //var mVar_c: Int = null 编译器报错，如果要初始化为null，则需要在类型中声明数据可以为null
    var mVar_c: Int? = null
    val mVal_d: String? = null

    init {
        mVar_c = 10
        //mVal_d = "val可空变量" 编译器报错，val类型数据不能更改

        Log.e(TAG, "可空变量：mVar_c == $mVar_c | mVal_d == $mVal_d")
    }

    //3.延迟初始化
    //Kotlin 编译器不会对 `lateinit` 修饰的变量做空检查，它就会默认你会初始化的，至于你什么时候怎么初始化它就不管了。

    //var nameA: Int//编译器报错，不能用于Int，Float，Double等数据类型
    //var nameB: String//编译器报错，必须初始化或者加abstract修饰
    //lateinit val nameC: String//编译器报错，lateinit不能修饰val
    lateinit var nameD: String

    lateinit var tvName: TextView//使用lateinit修饰避免!! 非空断言
//    var tvName: TextView? = null //tvName需要加 !! 非空断言


    //4.惰性初始化
    //变量后需要使用by链接，当变量用到的时候才会初始化lazy{}里面的内容，而且在再次调用时，只会得到结果，而不会再执行lazy{}的运行过程。

    //声明一个惰性初始化的集合
    val mLists: ArrayList<String> by lazy {
        arrayListOf("aa", "bb", "cc")
    }

    //声明一个惰性初始化的字符串
    val mStr: String by lazy {
        Log.e(TAG, "-----mStr惰性初始化-----")
        "山水有相逢"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_grammar)

        tvName = TextView(this)
        tvName.text = "延迟初始化"

        baseUsage()

        Log.e(TAG, "惰性初始化：mLists地址是否相同 == ${mLists == mLists}, 内容 == $mLists")
        Log.e(TAG, "惰性初始化：mStr == $mStr")
        Log.e(TAG, "惰性初始化：mStr == $mStr")

        Log.e(TAG, "常量：constA == $constA")
        Log.e(TAG, "常量：constB == ${Constants.constB}")
        Log.e(TAG, "常量：constC == $constC")
    }

    /**
     * 5.基础用法
     */
    fun baseUsage() {
        //Var可变变量
        var varA: Int = 2//立即初始化

        var varB = 5//推导类型 如果不声明类型，系统会根据你的赋值自动识别该数据类型，如5.0自动识别为Float类型

        var varC: Float//没有初始化的时候必须声明类型
        varC = 11.3f
        varC += 1

        Log.e(TAG, "var 可变变量：varA == $varA | varB == $varB | varB == $varC")

        //Val 不可变变量
        val valD: Int = 0//立即初始化

        val valE = "字符串"//推导类型 如果不声明类型，系统会根据你的赋值自动识别该数据类型，如valE类型为字符串类型

        val valF: Float//没有初始化的时候必须声明类型
        valF = 0.5f
        //valF += 0.1f//编译报错，因为valF是不可变变量

        Log.e(TAG, "val 不可变变量：valD == $valD | valE == $valE | valF == $valF")
    }

    //1.单行注释

    /*
       2.多行注释(块注释)
     */

    /*
        3.多行注释嵌套
        第一层
        /*
            第二层

            */
      */

}