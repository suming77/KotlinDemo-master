package com.suming.kotlindemo
import androidx.appcompat.app.AppCompatActivity

/**
 * @创建者 mingyan.su
 * @创建时间 2019/4/8 15:26
 * @类描述 ${TODO}Kotlin拓展
 */
class ExpandActivity : AppCompatActivity() {
    //kotlin可以对一个类的方法和属性进行拓展，且不需要继承或者使用Decorator模式
    //拓展是一种静态行为，对被拓展的类代码不会造成任何影响

    //1.拓展函数
    //拓展函数可以在已有类中添加新的方法，不会对原类做修改，拓展函数的定义形式：
//    fun receiverType : functionName(params){
//        body
//    }

//    receiverType:表示函数的接收者，也就是函数拓展的对象
//    functionName:拓展函数的名称
//    params:拓展函数的参数，可以为null

    //例子：
    class User(var name: String)

    /*拓展函数*/
    fun User.Print() {
        print("用户名:$name")
    }

    fun main(args: Array<String>) {
        val user = User("刘亦菲")
        user.Print()
    }//输出结果：用户名:刘亦菲

    //下面代码为MutableList添加一个swap函数
    //拓展函数swap，调换不同位置的值
    fun MutableList<Int>.swap(index: Int, index2: Int) {
        val tmp = this[index] //this对应该列表
        this[index] = this[index2]
        this[index2] = tmp
    }

    fun main2(args: Array<String>) {
        val l = mutableListOf(1, 2, 3)
        //位置0和2的值互换
        l.swap(0, 2)//‘swap()’函数内的‘this’指向‘l’的值
        print(l.toString())
    }//输出结果：[3,2,1]

    //this关键字指待接收者对象（receiver object）(也就是调用拓展函数时，在点号之前指定的对象实例)


    //2.拓展函数时静态解释的
    //拓展函数时静态解析的，并不是接受者成员的虚拟成员，在调用拓展函数时，具体被调用的是哪个函数，
    //由调用函数的对象表达式来决定的，而不是动态类型来决定的

    open class C
    class D : C()

    fun C.foo() = "C" //拓展函数
    fun D.foo() = "D" //拓展函数

    fun printFoo(c: C) {
        println(c.foo())
    }

    fun main3(args: Array<String>) {
        printFoo(D())
    }//输出结果：C

    //若成员函数和拓展函数一致，则 使用函数时会优先使用成员函数
    class F {
        fun foo() {
            print("成员函数")
        }
    }

    fun F.foo() {
        print("拓展函数")
    }

    fun main4(args: Array<String>) {
        val f = F()
        f.foo()
    }//输出结果：成员函数


    //3.拓展一个空对象
    //在拓展函数中，可以通过this来判断接受者是否为null，即使接受者为null也可以调用拓展函数
    fun Any?.toString(): String {
        if (this == null) return "null"
        //空检查之后，this会自动转为非空类型，所以下面的toString()解析为Any类的成员函数
        return toString()
    }

    fun main5(args: Array<String>) {
        var t = null
        print(t.toString())
    }//输出结果：null

    //拓展属性
    //除了函数，kotlin也支持属性对属性进行拓展
    val <T> List<T>.lastIndex: Int
        get() = size - 1

    //拓展属性允许定义在类或者kotlin文件中，不允许定义在函数中，初始化属性因为属性没有后端字段（backing field）
    //所以不能被初始化，只能由显示提供get/set定义
//    val Foo.bar = 1//错误，拓展属性不能有初始化值
    //拓展属性只能被声明为val


    //4.伴生对象的拓展
    //如果一个类定义有一个伴生对象，也可以对伴生对象定义拓展函数和属性
    //伴生对象通过“类名.”形式调用伴生对象，伴生对象的拓展函数通过类名限定符来调用

    class MyClass {
        companion object {}//将被称为Companion
    }

    fun MyClass.Companion.foo() {
        println("伴生对象的拓展函数")
    }

    val MyClass.Companion.no: Int
        get() = 10

    fun main6(args: Array<String>) {
        println("no:${MyClass.no}")
        MyClass.foo()
    }//输出结果：no:10 伴生对象的拓展函数


    //5.拓展的作用域
    //通常拓展函数和属性在顶级包下面
//    package foo.bar
//    fun Baz.goo(){……}
    //要使用定义包外的一个拓展，通过import导入拓展函数进行使用
//    package com.example.usage
//    import foo.bar.goo // 导入所有名为 goo 的扩展
//    // 或者
//    import foo.bar.*   // 从 foo.bar 导入一切
//
//    fun usage(baz: Baz) {
//        baz.goo()
//    }


    //6.拓展声明为成员
    //在一个类内部你可以为另一个类声明拓展
    //在这个拓展中，有多个隐含的接受者，其中拓展方法定义所在类的实例被称为分发接受者，而拓展方法的目标类型的实例被称为拓展接受者
    class E {
        fun bar() {
            println("E:bar")
        }
    }

    class H {
        fun baz() {
            println("F:baz")
        }

        fun E.foo() {
            bar()//调用E.bar
            baz()//调用H.baz
        }

        fun caller(e: E) {
            e.foo()//调用拓展函数
        }
    }

    fun main7(args: Array<String>) {
        val e = E()
        val h = H()
        h.caller(e)
    }//输出结果：E:bar F:baz

    //在H内创建了E函数的拓展，H被称为分发接受者，而E为拓展接受者，可以清楚看到，在拓展函数中，可以调用派发接受者的成员函数
    //如果调用某一个函数，而该函数在分发接受者和拓展接受者均存在，则以拓展接受者优先，要引用分发接受者的成员你可以使用限定的this语法

    class M {
        fun bar() {
            println("M:bar")
        }
    }

    class N {
        fun bar() {//与M类同名的方法
            println("N:bar")
        }

        fun M.boo() {
            bar()//调用同名方法，拓展接受者优先M.bar
            this@N.bar()//N.bar
        }

        fun caller(m: M) {
            m.boo()//调用拓展函数
        }
    }

    fun main8(args: Array<String>) {
        val n = N()
        val m = M()
        n.caller(m)
    }//输出结果：M:bar N:bar

    //以成员形式定义的拓展函数，可以声明为open，而且可以在子类中覆盖，在这类拓展函数的派发过程中，针对分发接受者是虚拟的(virtual)，但针对拓展接受者还是静态的
    open class O {

    }

    class O1 : O() {

    }

    open class P {
        open fun O.foo() {
            println("O:foo in P")
        }

        open fun O1.foo() {
            println("O1:foo in P")
        }

        fun caller(o: O) {
            o.foo()//调用拓展函数
        }
    }

    class P1 : P() {
        override fun O.foo() {
            println("O:foo in P1")
        }

        override fun O1.foo() {
            println("O1:foo in P1")
        }
    }

    fun main9(args: Array<String>) {
        P().caller(O())
        P1().caller(O())
        P().caller(O1())
    }
    //输出结果：O:foo in P
    //输出结果：O:foo in P1 --分发接受者虚拟解析
    //输出结果：O:foo in P  -- 拓展接受者静态解析

    //7.伴生对象
    //伴生对象的成员相当于java中的静态成员，其生命周期伴随类的始终，在伴生对象内部可以定义变量和函数，这些变量和函数可以直接用类名引用
    //对于伴生拓展函数有两种形式：类内拓展和类外拓展，这两种函数的拓展后的函数互不影响，甚至可以同名，即使是同名，他们也是两个不一样的函数
    //并具有以下特点：
    //1.类内拓展的伴随对象函数和类外拓展的伴随对象函数可以同名，是独立的两个函数，互不影响
    //2.同名时，类内的其他函数优先引用类内拓展的伴随对象函数，即对于类内其他成员函数来说，类内拓展屏蔽类外拓展
    //3.类内拓展的伴随对象函数只能被类内的函数引用，不能被类外的函数和伴随对象函数引用
    //4.类外的伴随对象函数可以被伴随对象内的函数引用
    //例子：

    class MyDemo {
        companion object {
            val myDemoField1: Int = 1
            var myDemoField2 = "this is myDemoField2"
            fun companionFun1() {
                println("this is 1st companion function")
//                foo()
            }

            fun companionFun2() {
                println("this is 2st companion function")
                companionFun2()
            }
        }

        fun MyDemo.Companion.foo() {
            println("伴随对象的拓展函数（内部）")
        }

        fun test2() {
            MyDemo.foo()
        }

        init {
            test2()
        }
    }

    val MyDemo.Companion.no: Int
        get() = 10

    fun MyDemo.Companion.foo() {
        println("foo 伴随函数外部拓展函数")
    }

    fun main10(args: Array<String>) {
        println("no:${MyDemo.no}")
        println("field1:${MyDemo.myDemoField1}")
        println("field2:${MyDemo.myDemoField2}")
        MyDemo.foo()
        MyDemo.companionFun2()
    }
    //输出结果：no:10
    //field1:1
    //field2:this is myDemoField2
    //foo 伴随函数外部拓展函数
    //this is 2st companion function
    //this is 1st companion function
    //foo 伴随函数外部拓展函数
}