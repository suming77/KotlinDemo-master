package com.suming.kotlindemo.blog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @创建者 mingyan.su
 * @创建时间 2020/09/29 17:30
 * @类描述 {$TODO}枚举类
 */
class EnumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //不需要实例化枚举类就可以访问枚举常量，通过 `枚举类.常量名` 调用。
        Direction.NORTH
        Direction.SOUTH
        Direction.WEST
        Direction.EAST

        ConsoleColor.BLACK

        State.NORMAL
        State.ERROR

        /**
         * 每个枚举常量都有属性来获取它在枚举类声明中的名称和位置，包括 `name（枚举常量名）` 和 `ordinal（枚举常量位置）`。
         */
        val normal = State.NORMAL.name
        val normalIndex = State.NORMAL.ordinal

        val error = State.ERROR.name
        val errorIndex = State.ERROR.ordinal

        println("枚举常量的使用：name == $normal | ordinal == $normalIndex ")
        println("枚举常量的使用：name == $error | ordinal == $errorIndex ")

        /**
         *Kotin 中的枚举类有合成方法，允许列出定义的枚举常量，并通过名称获得枚举常量。这些方法的签名如下：
         */
        val normalName = State.valueOf("NORMAL")
        val errorOrdinal = State.values()[1]

        println("枚举常量的使用：name == $normalName")
        println("枚举常量的使用：name == $errorOrdinal")

        /**
         * 从 Kotlin1.1 开始，就可以使用 `enumValues<T>()` 和 `enumValueOf<T>()` 结合泛型的方式来访问枚举类中的常量：
         */
        val stateName = enumValues<State>().joinToString { it.name }
        val stateError = enumValueOf<State>("ERROR")

        println("枚举常量的使用：stateName == $stateName")
        println("枚举常量的使用：stateError == $stateError")

    }

    /**
     * 枚举类最基本的用法是实现类型安全的。枚举类使用 enum 关键字修饰：
     */
    enum class Direction {
        NORTH, SOUTH, WEST, EAST//每一个枚举常数都是一个对象，枚举常量用逗号分隔。
    }

    /**
     * 通过枚举类构造函数，那么每一个枚举都有该构造函数，并且需要初始化值。
     */
    enum class Color(val rgb: Int) {
        RED(0xFF0000),
        GREEN(0x00FF00),
        BLUE(0x0000FF)
    }

    /**
     * 枚举常量的匿名类
     *
     * 枚举常量还可以用它们对应的方法声明它们自己的匿名类，以及覆盖基本方法。
     *  - 枚举常量之间使用逗号 `,` 分隔，如果还定义有任何成员(变量和方法)，则使用分号 `;` 将常量定义和成员定义分隔；
     *  - 必须提供一个抽象方法，定义在枚举类内部，而且必须在枚举常量后面。
     *
     *  注意：枚举类不能包含内部类以外的嵌套类型。
     */
    enum class ConsoleColor(var argb: Int) {
        RED(0xFF0000) {
            override fun print() {//必须重写的
                println("枚举常量 RED")
            }
        },
        WHITE(0xFFFFFF) {
            override fun print() {
                println("枚举常量 WHITE")
            }
        },
        BLACK(0x000000) {
            override fun print() {
                println("枚举常量 BLACK")
            }
        },//枚举变量之间使用逗号“,”分隔，最后一个变量必须使用分号分隔“;”
        GREEN(0x00FF00) {
            override fun print() {
                println("枚举常量 GREEN")
            }
        };

        abstract fun print()//抽象方法，定义在枚举类内部，而且必须在枚举变量后面
    }

    interface ShareListener {
        fun invite()
    }

    /**
     * 在枚举类中实现接口
     *
     * 和 Java 一样，Kotlin 的枚举类可以实现接口。一个枚举类可以实现一个接口，为所有枚举常量提供一个接口成员的实现，
     * 或者为其匿名类中的每个条目提供单独的接口成员实现。这是通过枚举类声明中添加接口来实现的。如下：
     */
    enum class State : ShareListener {
        NORMAL {
            override fun invite() {
                println("实现接口 NORMAL")
            }
        },
        ERROR;

        override fun invite() {
            println("实现接口 enum")
        }
    }
}