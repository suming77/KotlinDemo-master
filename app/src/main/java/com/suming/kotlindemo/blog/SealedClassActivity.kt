package com.suming.kotlindemo.blog

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.suming.kotlindemo.normal.share
import kotlin.math.log

/**
 * @创建者 mingyan.su
 * @创建时间 2020/08/21 17:07
 * @类描述 {$TODO}密封类
 *
 * 密封类用于表示受限制的类层次结构，类中的值可以拥有受限集合中的一种类型，但不能拥有任何其他类型时。
 * 从某种意义上看，他们是枚举类的拓展：枚举类型的值集合也受到限制，但每个枚举常量仅作为单个实例存在，
 * 而一个密封类的子类可以有多个可以包含状态的实例。
 */
class SealedClassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //var sealed = BaseSealed() //报错，密封类不能被实例化

        var people1 = BaseSealed.People("Android")
        var people2 = BaseSealed.People("Kotlin")

        println("密封类：people1 == $people1 | people2 == $people2")

        /**
         * 2.子类的类的拓展
         * 注意：扩展密封类（间接继承）的子类的类可以放在任何地方，而不一定是在同一个文件中。
         */
        var name = BaseSealed.Student.share("Java")
        println("密封类子类拓展：name == $name")

        invite(BaseSealed.Student)
    }

    /**
     * 1. 要声明一个密封类，可以在类名之前加上 sealed 修饰符。
     * 一个密封类可以有子类，但是所有子类都必须在同一个文件中声明，作为密封类的本身。
     * 密封的类本身是抽象的，它不能直接实例化，可以有抽象成员。
     * 密封类不允许有非私有构造函数（它们的构造函数默认是私有的）。
     */
    sealed class BaseSealed {
        //定义的子类必须继承于密封类，表示一组受限的类。
        data class People(val name: String) : BaseSealed()

        object Student : BaseSealed()//单列模式
    }

    //object Dragon : BaseSealed()

    /**
     * 3.当你在 when 表达式中使用密封类时，使用密封类的主要好处就发挥了作用。如果可以验证语句是否涵盖所有情况，
     * 则不需要向语句中添加 else 子句。但是，只有当你使用 when 作为表达式（使用结果）而不是作为语句时，这个方法才有效。
     */
    private fun invite(base: BaseSealed) = when (base) {
        is BaseSealed.Student ->
            println("密封类when：BaseSealed.Student ")
        is BaseSealed.People ->
            println("密封类when：BaseSealed.People")
    }

}