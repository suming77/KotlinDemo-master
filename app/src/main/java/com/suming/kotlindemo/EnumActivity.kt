package com.suming.kotlindemo

/**
 * @创建者 mingyan.su
 * @创建时间 2019/4/10 15:26
 * @类描述 ${TODO}枚举类
 */
class EnumActivity {
    //1.枚举类最基本的用法是实现一个类型安全的枚举
    //枚举常量用,分割开，每一个枚举常量都是一个对象
    enum class Color {
        RED, BLACK, BLUE, GREEN, YELLOW
    }

    //每一个枚举都是枚举的实例，都可以被动初始化
    enum class Color2(val rgb: Int) {
        RED(0xFF0000),
        GREEN(0x00FF00),
        BLUE(0x0000FF)
    }

    //默认名称为枚举字符名，值从0开始，若需要指定值，则可以使用其构造函数
    enum class Shape(value: Int) {
        ovel(100), rectangle(200)
    }

    //枚举还声明自己的匿名类及相应方法，以及覆盖基类的方法
    //如果枚举类定义任何成员，要使用分号将成员定义中的枚举常量分隔开来
    enum class ProtocolState {
        WAITING {
            override fun single() = TALKING
        },

        TALKING {
            override fun single() = WAITING
        };

        abstract fun single(): ProtocolState
    }


    //2.使用枚举常量
    //kotlin的枚举类具有合成方法，允许遍历定义的枚举常量，并通过其名获取枚举常数
//    EnumClass.valueOf(value: String): EnumClass  // 转换指定 name 为枚举值，若未匹配成功，会抛出IllegalArgumentException
//    EnumClass.values(): Array<EnumClass>        // 以数组的形式，返回枚举值

    //获取枚举相关信息：
//    val name: String //获取枚举名称
//    val ordinal: Int //获取枚举值在所有枚举数组中定义的顺序

    enum class Color3 {
        RED, BLACK, BLUE, GREEN, WHITE
    }

    fun main(args: ArrayList<String>) {
        var color: Color3 = Color3.BLACK

        println(Color3.values())// 以数组的形式，返回枚举值
        println(Color3.valueOf("RED"))// 转换指定 name 为枚举值，若未匹配成功，会抛出IllegalArgumentException
        println(color.name)//获取枚举名称
        println(color.ordinal) //获取枚举值在所有枚举数组中定义的顺序
    }

    //自kotlin1.1起，可以使用enumValues<T>()和enumValueOf<T>()函数以泛型的方式访问枚举中的常量：
    enum class RGB { RED, GREEN, BLUE }

    inline fun <reified T : Enum<T>> printAllValues() {
        print(enumValues<T>().joinToString { it.name })
    }

    fun main2(args: ArrayList<String>) {
        printAllValues<RGB>()
    }//输出：RED, GREEN, BLUE
}