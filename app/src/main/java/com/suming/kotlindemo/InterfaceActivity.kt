package com.suming.kotlindemo

/**
 * @创建者 mingyan.su
 * @创建时间 2019/4/4 18:30
 * @类描述 ${TODO}接口
 */
class InterfaceActivity {
    //1.实现接口
    //接口与java8类似，使用interface定义接口，允许方法有默认实现
    interface MyInterface {
        fun bar()//未实现的方法体
        fun foo() {//已实现
            println("foo--")//可选方法体
        }
    }

    //一个类或者对象可以实现一个或者多个接口
    class Child : MyInterface {
        override fun bar() {
            //方法体
            println("bar--")
        }
    }

    fun main(args: Array<String>) {
        val c = Child()
        c.foo()
        c.bar()
    }
    //输出：foo--bar--

    //2.接口中得属性
    //接口中的属性只能是抽像的，不允许初始化值，，接口不会保存属性值，实现接口时必须重写属性


    interface IMyInterface {
        var name: String//name属性，抽象的
    }

    class myImpl : IMyInterface {
        override var name: String = "接口属性必须重写"
    }

    //例子
    interface IMyInterfaceExample {
        var name: String
        fun bar()
        fun foo() {
            println("foo")//可选方法体
        }
    }

    class child : IMyInterfaceExample {
        override var name: String = "IMyInterfaceExample"//重写属性
        override fun bar() {
            println("bar")//方法体
        }
    }

    fun main2(args: Array<String>) {
        val v = child()
        println(v.name)
        v.foo()
        v.bar()
    }//输出IMyInterfaceExample foo bar

    //3.函数接口重写
    //实现多个接口时，可能遇到同一方法继承多个实现问题
    //例子


    interface IA {
        fun foo() {//已实现
            println("IA:foo")
        }

        fun bar()//未实现。没有方法体，是抽象的
    }

    interface IB {
        fun foo() {//已实现
            println("IB:foo")
        }

        fun bar() {//已实现
            println("IB:bar")
        }
    }

    interface IC : IA {
        override fun bar() {//重写
            println("IC:bar")
        }
    }

    class CC : IA,IB{
        override fun foo(){
            super<IA>.foo()
            super<IB>.foo()
        }

        override fun bar(){
            super<IB>.bar()
        }
    }

    fun main3(args: Array<String>){
        val cc = CC()
        cc.foo()
        cc.bar()
    } //输出为：IA:foo  IB:foo  IB:bar
    //实例AB中都定义了foo和Bar方法,两者都实现了foo方法，B实现了bar(),因为c是一个实现了A的具体类，
    // 所以必须重写bar(),并实现这个抽象方法，如果从A个B派生D,我们需要多个接口继承所有方法，并指明D应该如何去实现他们
    //这个规则，既适用用于继承单个实现(bar())的方法也适用于继承多个实现（foo()）的方法

}