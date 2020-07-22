package com.suming.kotlindemo.blog

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.suming.kotlindemo.User

/**
 * @创建者 mingyan.su
 * @创建时间 2020/07/08 17:42
 * @类描述 {$TODO}逻辑控制语句
 */
class LogicControlGrammarActivity : AppCompatActivity() {
    private val TAG = "LogicControlActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        ifGrammar()
//        forGrammar()
//        whenGrammar()
//        whileGrammar()
//        returnGrammar()
        breakContinueGrammar()
    }

    /**
     * 配合和标签使用
     * 在 Kotlin 中任何表达式都可以使用标签来标记(label)，标签的形式是标签名后接@标识符，如flag@等，标签符由自己决定
     *  break 语句：终止最直接包围它的循环。
     *  continue 语句：继续下一次最直接包围它的循环。即跳出本次循环，即系下一次循环。
     */
    private fun breakContinueGrammar() {
        //基本用法
        for (i in 1..10) {
            if (i == 4) continue//i == 4时，跳出当前循环，进入下一次循环
            Log.e(TAG, "break和continue：i == $i")
            if (i > 6) break//i > 6时跳出循环
        }

        //使用一个标签来定义一个break或continue：

        Log.e(TAG, "=== break和标签一起使用 ===")
        loop1@ for (i in 1..10) {
            for (j in 1..10) {
                if (true) {
                    Log.e(TAG, "break: i + j == $i + $j")
                    break@loop1//跳出@loop1循环
                }
            }
        }

        Log.e(TAG, "=== continue和标签一起使用 ===")
        loop2@ for (i in 1..10) {
            for (j in 1..10) {
                if (true) {
                    Log.e(TAG, "continue: i + j == $i + $j")
                    continue@loop2//跳出本次循环，进入下一个@loop2循环
                }
            }
        }
    }

    /**
     *  return 语句：默认从直接包围它的函数或者匿名函数返回。
     */
    private fun returnGrammar() {
        var intArrays = arrayListOf(1, 2, 3, 4)
//        for (item in intArrays) {
//            if (item == 2) return//非本地，直接return给returnGrammar()的调用者
//            Log.e(TAG, "return：item == $item")
//        }
//        Log.e(TAG, "return：外层函数")

        //数组的迭代器理由有一个Iterable.forEach()方法，传入了一个it参数，为数组中的元素
        //1.标签和return一起使用
        intArrays.forEach flag@{
            if (it == 2) return@flag//本地返回给lambda的调用者，即forEach循环
            Log.e(TAG, "return：item == $it")
        }
        Log.e(TAG, "return：外层函数")

        //2.使用隐式标签，该标签与接受该 lambda 的函数同名
        intArrays.forEach {
            if (it == 2) return@forEach//本地返回给lambda的调用者，即forEach循环
            Log.e(TAG, "return：item == $it")
        }
        Log.e(TAG, "return：外层函数")

        //3.使用匿名函数代替 lambda 表达式，返回是从匿名函数中返回而不是外层函数
        intArrays.forEach(fun(value: Int) {
            if (value == 2) return//本地返回给匿名函数调用者，即forEach循环
            Log.e(TAG, "return：value == $value")
        })
        Log.e(TAG, "return：外层函数")

        //4.注意前三个示例中使用的本地return类似于在常规循环中使用的continue。
        //对于break没有直接的等价，但是可以同过添加另一个嵌套lambda和费本地return来模拟：
        run loop@{
            intArrays.forEach {
                if (it == 2) return@loop//非本地返回运行的lambda表达式
                Log.e(TAG, "return：@loop == $it")
            }
        }
        Log.e(TAG, "return：外层函数")
    }

    /**
     *  while 语句：Kotlin 中的while语句与 Java 中的while语句用法相同
     *  while语句如果不满足条件是不会进入循环的，不同的是do…while是先执行循环再判断条件，循环至少会执行一次。
     */
    private fun whileGrammar() {
        var numA = 1
        while (numA < 6) {
            Log.e(TAG, "while语句：循环了$numA 次")
            numA++
        }

        var numB = 0
        do {
            Log.e(TAG, "do...while语句：循环了$numB 次")
            numB--
        } while (numB > 0)
    }


    /**
     *  when 语句：
     *  when将它的参数和所有分支条件比较，直到某个分支满足条件，跳出语句。when既可以当做表达式使用也可以当做语句使用，
     *  如果被当做表达式，那么符合条件的分支的值就是整个表达式的值；如果当语句使用则忽略分支的值。
     *  when操作符类似 Java 中的 switch 操作符。
     */
    private fun whenGrammar() {
        //1.when操作符用 -> 表示要执行的操作，类似switch 的：，同时每个分支不需要像switch语句那样添加break跳出分支，
        // 如果分支下面只有一个语句，则可以省略块符号{}，否则需要加上块符号{}将要执行的操作包起来。
        var num = 0
        when (num) {
            2 ->
                print("when语句：num == 2")
            4 -> {
                print("when语句：num == 4")
            }
            else -> {//注意块
                print("when语句：num != 2 && num != 4")
            }
        }

        //2.如果很多分支用相同的方式处理，则可以把多个分支条件放在一起，用,分隔开来，相当于 switch 中不用break跳转的语句：
        num = 2
        when (num) {
            2, 4 -> {
                print("when语句：num == 2 or num == 4")
            }
            else -> print("when语句：num != 2 && num != 4")
        }

        //3.when 语句中的条件可以使用任意表达式，并不是只局限于常量，相当于 if 表达式的用法：
        when (num > 0) {
            true -> print("when语句：num > 0")
            false -> print("when语句：num < 0")
        }

        //4.我么也可以检查一个条件是否在in或者不在!in一个区间或者集合中，in表示在…范围内，!in表示不在…范围内。
        num = 5
        val intArray = arrayOf(6, 7, 8, 9, 10)
        when (num) {
            in 1..5 -> print("when语句：num == 属于 1 ~ 5 中")
            !in intArray -> print("when语句：num == 不在intArray集合中")
            else -> print("when语句：num == 都不属于")
        }

        //5.检测一个值是is或者不是!is一个特定的类型。注意：kotlin 的智能转换，可以访问该类型的属性和方法而不需要任何的检测。
        fun isWhen(x: Any) = when (x) {
            is Int -> print("when语句：x 是Int类型")
            !is String -> print("when语句：x 不是String类型")
            else -> {
                print("when语句：x 是String类型")
            }
        }

        isWhen("山水有相逢")

        //6.when 也可以用来取代if … else链，如果不提供参数，所有的分支条件都是简单的布尔表达式，而当一个分支条件为true时，则执行该分支，跳出语句。
        var str = "kotlin"
        val strArray = arrayOf("android", "kotlin", "java")
        when {
            str is String -> print("when语句：str 是String类型")
            "java" in strArray -> print("when语句：java == 在集合中")//in运算符还可以判断集合内是否包含某实例
        }

    }

    /**
     * for 语句:Kotlin `for`循环语句提供迭代器来遍历任何东西，循环数组会被编译为一个基于索引的循环，
     * 不会创建迭代器对象。`for`循环语句废除了 Java 中的(初始值;条件;增减步长)规则，新增了其他规则。
     * 其语法如下：
     * for (item in collection){
     *       print(item)
     * {
     */
    private fun forGrammar() {

//        //java 的for循环递增
//        for (int i = 0; i < 5; i++) {
//            System.out.println(i);
//        }

        //..创建从此值到指定值的范围。表示一个区间，该区间是闭区间，包括开始值和结束值，[n,m]。
        for (i in 1..5) {//1   2	3	4	5
            print("$i \t")
            Log.e(TAG, "for语句：..递增 == $i")
        }

        //until: 创建从此值但不包括指定的值的范围。表示一个区间，该区间是半闭区间，包括开始值，不包括结束值，[n,m)。
        for (i in 1 until 5) {//1	2	3	4
            print("$i \t")
            Log.e(TAG, "for语句：until递增 == $i")
        }

        //downTo:通过步长-1返回从该值向下到指定的值的序列。表示一个区间，该区间是闭区间，包括开始值和结束值，[n,m]。
        for (i in 5 downTo 1) {
            print("$i \t")
            Log.e(TAG, "for语句：downTo递减 == $i")
        }

        //step:返回与给定步骤执行相同范围的进程。也就是说取值的间隔是多少。
        for (i in 6 downTo 1 step 2) {//步长为2，循环两次的递减
            print("$i \t")
            Log.e(TAG, "for语句：step步长为2 == $i")
        }

        //遍历字符串
        for (i in "HelloWord") {
            print("$i \t")
            Log.e(TAG, "for语句：字符串 == $i")
        }

        //遍历集合
        val userArray = arrayListOf(User("姓名1", 28, "ss"), User("姓名2", 28, "ss"), User("姓名3", 28, "ss"))
        for (item in userArray) {
            print("${item.name} \t")
            Log.e(TAG, "for语句：遍历集合 == ${item.name}")
        }

        //indices：返回此集合的有效索引的`[int范围]`。
        val intArray = arrayListOf(10, 20, 30, 40)
        for (i in intArray.indices) {
            Log.e(TAG, "for语句：indices遍历 == intArray[$i]：${intArray[i]}")
        }

        //withIndex:返回一个惰性的`[Iterable]`，它将原始集合的每个元素包装成一个`IndexedValue`对象，其中包含该元素和元素本身的索引。
        for ((index, value) in intArray.withIndex()) {
            Log.e(TAG, "for语句：withIndex == index: $index, value: $value")
        }
    }

    /**
     * if 语句
     * Kotlin 中的`if`语句的传统用法与 Java 中的用法一样
     * Kotlin 中其实不存在 Java 中的三元运算符,if语句可以作为一个表达式并且返回一个值。
     */
    private fun ifGrammar() {
        var numA = 5
        val numB = 10
        var result = 0

        if (numA == 5) result = numA

        if (numA > numB) {
            result = numA
        } else {
            result = numB
        }

        //Java 的三目运算符   Kotlin中这样写会报错
        //result = numA > numB ? 10 : 20;

        //kotlin 中直接使用 if…else…替代
        result = if (numA > numB) {
            10
        } else {
            20
        }

        //简写为
        result = if (numA > numB) 10 else 20  //当numA>0时，result = 10，否则result = 20
        Log.e(TAG, "if语句：result == $result")
    }

}