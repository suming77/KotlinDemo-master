package com.suming.kotlindemo.blog

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.suming.kotlindemo.User

/**
 * @创建者 mingyan.su
 * @创建时间 2020/07/04 18:01
 * @类描述 {$TODO}数据类型
 */
class DataTypeActivity : AppCompatActivity() {
    private val TAG = "DataTypeActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        normalUsage()
//        hexUsage()
//        underlineUsage()
//        compareValue()
//        typeTransform()
//        operaUsage()
//        booleanOpera()
//        charOpera()
//        strOpera()
//        arrayUsage()
    }

    /**
     * 10.数组类型
     * arrayOf():
     * 创建一个包含指定元素的数组。参数 T 是一个可变参数的泛型对象，每个元素的类型不一定相同，可以是不同类型。
     *
     * arrayOfNulls():
     * 返回给定类型和给定大小的对象数组。所有元素初始化为 null ，参数 size 表示数组的大小。
     *
     * Array(size: Int, init: (Int) -> T)：
     * 用指定的size创建一个新的数组，其中每个元素通过调用指定的[init]函数计算。
     * Array()的第一个参数 size 是指定数组的大小，第二个参数 [init] 为使用其元素下标组成的表达式，
     * 是一个 (Int) -> T 类型函数，通常使用Lambda表达式，[init]函数的第一个参数是数组的下标(从0开始)，
     * 对每个数组元素按顺序从第一个开始调用的。
     */
    private fun arrayUsage() {
        var arrA = arrayOf(1, 2, 3, 4, 5)//等价于[1,2,3,4,5]
        for (a in arrA) {
            print(a)
            print("\t")
        }
        var arrB = arrayOf(1, "0", "a", 4.2f, 5, User("22", 1, "33"))
        for (b in arrB) {
            print(b)
            print("\t")
        }

        var arrC = arrayOfNulls<Int>(3)//数据类型为Int，元素个数为3
        //如果不给数组赋值则元素都为null，即arrC[0],arrC[1],arrC[2]都为null
        for (c in arrC) {//null,null,null
            print(c)
            print("\t")
        }

        //为数组arrC赋值
        arrC[0] = 10
        arrC[1] = 100
        arrC[2] = 1000
        for (c in arrC) {//10,100,1000
            print(c)
            print("\t")
        }

        //创建一个大小为5，调用指定的`[init]`函数`(index * 10).toString()`计算每个元素的数组
        //[init]函数第一个参数是数组下标，第二个参数是函数计算表达式
        var arrD = Array(5, { index -> (index * 10).toString() })
        for (d in arrD) {//0,10,20,30,40
            print(d)
            print("\t")
        }

        //基本数据类型数组，以Int为例
        //1)创建有1,2,3,4构成的基本数据类型数组
        var intArr1 = intArrayOf(1, 2, 3, 4)
        //2)创建4个元素为0的基本数据类型数组
        var intArr2 = IntArray(4)
        //3)创建3个元素，元素值为下标的两倍的数组
        var intArr3 = IntArray(3, { index -> (index * 2) })

        for (item in intArr1) {//1, 2, 3, 4
            print(item)
            print("\t")
        }
        for (item in intArr2) {//0,0,0,0
            print(item)
            print("\t")
        }
        for (item in intArr3) {//0,2,4
            print(item)
            print("\t")
        }
    }

    /**
     * 9.字符串类型
     */
    private fun strOpera() {
        //String 表示字符串类型，和 Java 一样，String 是不可变的，所以字符串的元素可以通过索引操作的字符：str[index]，
        // 使用for 循环迭代字符串，其中str为目标字符串，index为索引。
        val str: String = "HelloWord"
        for (s in str) {
            print(s)
            print("\t")
        }

        //在 Kotlin 中，字符串字面量有两种类型：
        //包含转义字符的字符串，转义包括\t、 \n等，不包含转义字符的也同属此类型；
        //包含任意字符的字符串，由三个引号 """ """ 表示， 支持多行字符串。

        //包含转义字符的
        val strA: String = "He llo\tWord"
        Log.e(TAG, "字符串类型：strA == $strA")

        //包含任意字符，三个引号 """ """
        val strB: String = """fun main(args: Array<String>){
                                        println("我是三重引号引用的字符串，我可以包含任意字符")
                                    }"""
        Log.e(TAG, "字符串类型：strB == $strB")

        val strTrim: String = """
                |HelloWord
                |Android
                |Kotlin
                |Java
                """
        Log.e(TAG, "字符串类型：trimMargin前 == $strTrim")
        Log.e(TAG, "字符串类型：trimMargin后 == ${strTrim.trimMargin()}")//前置空格被删除
        //可以通过trimMargin()来删除多余的空格，默认使用 | 作为边界前缀，也可以使用其他符合，
        // 例如左尖括号：trimMargin("<")，右尖括号：trimMargin(">")。

        //字符串模板
        //字符串可以包含模板表达式，即一些小段代码，会求值并合并到字符串中，模板表达式由美元符号开头$，
        //在$符号后面加上变量名或者大括号中的表达式。其语法为$变量或常量名或者${表达式}。

        //原生字符和转义字符的内部都支持模板，如果你需要在原生字符串中表示字面值 $，可以写成{'$'}或者\$。
        // 注意：三个引号 """ """ 表示的字符串不支持反斜杠转义，即\$无效。
        val temp = 10
        val tempA = "temp $temp"
        //花括号{}扩起来的任意表达式
        val tempB = "temp.length is ${temp.toString().length}"
        //原生字符和转义字符
        var tempC = "两个引号支持\$7777，也支持${'$'}108"
        val tempD = """
                三个引号不支持\$10000，只支持${'$'}998.99
                """
        Log.e(TAG, "字符串模板：普通值 == $tempA")
        Log.e(TAG, "字符串模板：任意表达式 == $tempB")
        Log.e(TAG, "字符串模板：特别注意 == $tempC")
        Log.e(TAG, "字符串模板：原生和转义字符 == $tempD")
    }

    /**
     * 8.字符型（Char）
     * Char 表示字符类型，与 Java 一样，Kotlin 中的 Char 不能直接和数字操作，字符变量使用单引号''表示，
     * 比如：'a'。不能直接视为数字，不过可以显式转换为数字。
     */
    private fun charOpera() {
        var charA: Char
        //charA = 1//编译错误：类型不兼容
        charA = 'a'
        Log.e(TAG, "字符型：charA == $charA")

        //字符型的变量不仅可以转换为数字，也可以转换为其他类型。
        val byteChar = charA.toByte()
        val shortChar = charA.toShort()
        val intChar = charA.toInt()
        val longChar = charA.toLong()
        val strChar = charA.toString()
        val floatChar = charA.toFloat()
        val doubleChar = charA.toDouble()
        Log.e(TAG, "字符型：byteChar == $byteChar | shortChar == $shortChar | intChar == $intChar" +
                " | longChar == $longChar | strChar == $strChar | floatChar == $floatChar | doubleChar == $doubleChar")

        //除了可以转换类型外，当字符变量为英文字母时还支持大小写转换。当字符变量不是英文字母时转换无效
        var char = charA.toUpperCase()//转大写
        Log.e(TAG, "字符型：小写转大写 == $char")

        char = charA.toLowerCase()//转小写
        Log.e(TAG, "字符型：大写转小写 == $char")

        charA = '1'
        char = charA.toLowerCase()//当字符变量不是英文字母时转换无效
        Log.e(TAG, "字符型：大小写转换无效 == $char")

        //与 Java 一样，特殊字符需要使用反斜杠`\`转义，支持这几个转义序列：\t、 \b、\n、\r、\'、\"、\\ 和 \$，
        //编码其他字符要使用 Unicode 转义序列语法：'\uFF00'。
        Log.e(TAG, "字符转义：\t == 制表符")
        Log.e(TAG, "字符转义：\b == 退格键")
        Log.e(TAG, "字符转义：\n == 换行符")
        Log.e(TAG, "字符转义：\n == 键盘上的Enter键")
        Log.e(TAG, "字符转义：\' == 单引号")
        Log.e(TAG, "字符转义：\" ==  双引号")
        Log.e(TAG, "字符转义：\\ == 反斜杠")
        Log.e(TAG, "字符转义：\$ == 美元符号")
        Log.e(TAG, "字符转义：\uFF01 == Unicode转义序列语法")
    }

    /**
     * 7.Boolean 操作符
     * Boolean 关键字表示布尔类型，并且有两个值：true、false。如果被需要可空引用，布尔会被装箱。
     *
     * Kotlin 的逻辑操作符与 Java 的相同：
     * - ||：   逻辑或(或者)，有任何一个条件为true，返回true，否则返回false；
     * - &&：   逻辑与(并且)，所有条件为true，返回true，否则返回false；
     * - !：   逻辑非(取反)， true取反为false，false取反为true。
     */
    private fun booleanOpera() {

        val booleanA: Boolean? = null
        val booleanB: Boolean = true
        val booleanC: Boolean = false
        Log.e(TAG, "布尔操作符：booleanA == $booleanA | booleanB == $booleanB | booleanC == $booleanC")

        var result: Boolean

        //逻辑或 ||
        result = booleanB || booleanC
        Log.e(TAG, "布尔操作符：booleanB || booleanC == $result")

        //逻辑与 &&
        result = booleanB && booleanC
        Log.e(TAG, "布尔操作符：booleanB && booleanC == $result")

        //逻辑非 !
        result = !booleanB
        Log.e(TAG, "布尔操作符：!booleanB == $result")
        result = !booleanC
        Log.e(TAG, "布尔操作符：!booleanC == $result")
    }

    /**
     * 6.位操作符
     * Kotlin 中的位操作符与 Java 有很大差别，Kotlin 中没有特殊的字符，但是只能命名为可以以中缀形式调用的函数
     * 注意：仅仅适用于 Int 和 Long 类型
     */
    private fun operaUsage() {
        val num: Int = 7
        var shlNum = num shl (2)
        var shrNum = num shr (2)
        var ushrNum = num ushr (2)
        var andNum = num and (2)
        var orNum = num or (2)
        var xorNum = num xor (2)
        var invNum = num.inv()

        Log.e(TAG, "位操作符：shlNum == $shlNum | shrNum == $shrNum | ushrNum == $ushrNum |" +
                " andNum == $andNum | orNum == $orNum | xorNum == $xorNum | invNum == $invNum")
    }

    /**
     * 5.类型转换
     *
     * 由于不同的表示方式，较小类型并不是较大类型的子类型，较小的类型不能隐式转换为较大的类型，
     * 这意味着在不进行显式转换的情况下我们不能把一个 `Byte` 型值赋给 `Int` 变量。
     */
    private fun typeTransform() {
        val byteA: Byte = 1
        //val IntA: Int = byteA//错误

        //显式转换
        val num: Int = 108
        val numInt: Int = num.toInt()
        val numLong: Long = num.toLong()
        val numFloat: Float = num.toFloat()
        val numDouble: Double = num.toDouble()
        val numByte: Byte = num.toByte()
        val numShort: Short = num.toShort()
        val numChar: Char = num.toChar()//108对应的字符是l
        val numString: String = num.toString()

        Log.e(TAG, "类型转换：numInt == $numInt | numLong == $numLong | numFloat == $numFloat")
        Log.e(TAG, "类型转换：numDouble == $numDouble | numByte == $numByte | numShort == $numShort")
        Log.e(TAG, "类型转换：numChar == $numChar | numString == $numString")

        //隐式转换
        //有些情况下也是可以使用自动类型换化的，前提是可以根据上下文环境推断出正确的数据类型而数学操作符会做出相应的重载。
        val numA = 18L + 10//Long + Int = Long

        Log.e(TAG, "隐式转换：numA == $numA")
    }

    /**
     * 4.数字加下划线用法
     * 三个等号 === 表示比较对象地址，两个 == 表示比较两个值大小。
     *
     * 注意：在 Kotlin 的缓存策略中，缓存的范围是-127~128，在范围 [-128, 127] 之间并不会创建新的对象，
     * 上面 compareA = 100001 定义的是 Int 类型，大于127所以地址值不同，反则相同。
     * 其实上面的装箱操作之后其内存中的地址根据数据类型的数值范围确定的。
     *
     * 比如： compareA = 10 ，在范围 [-128, 127] 内，不会创建新的对象，boxedA === boxedB 地址相同，输出为true。
     *
     */
    private fun compareValue() {
        val compareA: Int = 100001

        Log.e(TAG, "比较数值：compareA === compareA： " + (compareA === compareA).toString())//true，值相等，对象地址相等

        //装箱，创建两个不同的对象
        val boxedA: Int? = compareA
        val boxedB: Int? = compareA

        //虽然经过装箱，但是值都是10
        Log.e(TAG, "比较数值：boxedA === boxedB： " + (boxedA === boxedB).toString())//false，值相等，对象地址不相同
        Log.e(TAG, "比较数值：boxedA == boxedB： " + (boxedA == boxedB).toString())//true，值相等

        val compareB: Int = 10
        //装箱
        val boxedC: Int? = compareB
        val boxedD: Int? = compareB
        //虽然经过装箱，但是值都是10，在范围 [-128, 127] 内，不会创建新的对象

        Log.e(TAG, "比较数值：boxedC === boxedD： " + (boxedC === boxedD).toString())//true，值相等，对象地址相同

        //装箱与拆箱
        //简单一点来说，装箱 就是自动将基本数据类型转为包装器类型，拆箱 就是自动将包装器类型自动转为基本数据类型。

        // 在 Kotlin 中存在数字的装箱，不存在数字的拆箱。
        // 因为 Kotlin 是没有基本数据类型的，Kotlin 是万般皆对象的原则，
        // 所以不存在与 Java 中类似的 `int` 是数据类型，`Integer` 是整型的引用类型。

        //在 Kotlin 中要实现装箱操作，首先要了解可空引用，比如 `Int?`(只限数值类型)，表示这条数据可以为 `null`。

        val boxedE: Int = 123
        //装箱过程，其实装箱之后值是没有变化的
        val boxedF: Int? = boxedE

        Log.e(TAG, "装箱：boxedF == $boxedF")
    }

    /**
     * 3.数字加下划线用法
     */
    private fun underlineUsage() {
        val fiveMillion = 5_000_000                         //五百万
        val creditCardNumber = 1234_5678_9012_3456L         //银行卡号码
        val socialSecurityNumber = 999_99_9999L             //社会号码
        val hexBytes = 0xFF_EC_DE_5E                        //十六进制
        val hexTwo = 0b11010010_01101001_10010100_10010010   //二进制

        Log.e(TAG, "数字加下划线：五百万 fiveMillion == $fiveMillion")
        Log.e(TAG, "数字加下划线：银行卡号码 creditCardNumber == $creditCardNumber")
        Log.e(TAG, "数字加下划线：社会号码 socialSecurityNumber == $socialSecurityNumber")
        Log.e(TAG, "数字加下划线：十六进制 hexBytes == $hexBytes")
        Log.e(TAG, "数字加下划线：二进制 hexTwo == $hexTwo")
    }

    /**
     * 2.进制用法
     */
    private fun hexUsage() {
        //Kotlin不支持八进制
        var hexA = 0b00011011//二进制：0b或者0B前缀表示
        var hexB = 110//十进制
        var hexC = 0xEF//十六进制：0x或者0X前缀表示

        Log.e(TAG, "进制类型：二进制 hexA == $hexA | 十进制 hexB == $hexB | 十六进制 hexC == $hexC")
    }

    /**
     * 1.普通用法
     */
    private fun normalUsage() {
        var numI: Int = 10
        var numL: Long = 10L//长整型有大写字母L标记
        var numF: Float = 10f//单精度浮点型由小写字母f或者大写字母F标记
        var numD: Double = 10.0
        var numB: Byte = 10
        var numS: Short = 10

        Log.e(TAG, "数值类型：numInt == $numI | numLong == $numL | numFloat == $numF |" +
                " numDouble == $numD | numByte == $numB | numShort == $numS")
    }

}