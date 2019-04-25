package com.suming.kotlindemo

import android.view.View

/**
 * @创建者 mingyan.su
 * @创建时间 2019/4/9 12:30
 * @类描述 ${TODO}kotlin数据类和密封类
 */
class DataActivity {
    //1.数据类
    //kotlin可以创建一个只包含数据的类，关键字为data：
    data class User(val name: String, val age: Int)
    //编译器会自动地从主构造函数中根据所有声明的属性提取一下属性：
    //equals()/hashCode()
    //toString(),例如User(name=jon,age=12)
    //componentN() functions对应属性，按声明顺序排列
    //copy()函数

    //如果这些函数在类中已经被明确定义了，或者在超类中继承而来，就不会再生成，为了保证代码的一致性和意义，数据类需要满足一下条件：
    //主构造函数必须包含一个参数
    //所有主构造函数的参数必须标识为var或者val
    //数据类不可以声明为abstract inner open sealed
    //数据类不能继承其他类，但可以实现接口

    //复制使用copy()函数,我们可以使用该函数复制对象并修改部分属性，例如：
//    fun copy(name: String = this.name, age: Int = this.age) = User(name, age)

    fun main(args: Array<String>) {
        val jon = User(name = "jon", age = 12)
        val oldJon = jon.copy(name = "oldJon")
        println("jon")//User(name = "jon", age = 12)
        println("oldJon")//User(name = "oldJon", age = 12)
    }


    //2.数据类以及解构声明
    fun destruction() {
        //组件函数允许数据类在解构声明中使用
        val jack = User("jack", 42)
        val (name, age) = jack
        println("$name,$age years of age")
    }

    //标准库提供了Pair和Triple,在大多数情形中，命名数据类是更好的设计选择，因为这样代码可读性更强而且提供有意义的名字和属性

    //3.密封性
    //密封类表示受限的类继承机构，当一个值为有限几种类型，而不能有任何其他类型时，在某种意义上它是枚举的拓展，
    // 枚举类型的值集合也是受限的，但是每个枚举示例只存在一个实例，而密封类的子类可以有可包含状态的多个实例
    //声明一个密封类，使用sealed修饰类，密封类可以有子类，但是所有子类必须内嵌套在密封类中
    //sealed不能修饰interface abstract class(会报warning,但不会出现编译错误)
    sealed class Expr {

        data class Contst(val number: Double) : Expr()

        data class Sum(val e1: Expr, val e2: Expr) : Expr()

        object NotANumber : Expr()
    }

    fun eva(expr: Expr): Double = when (expr) {
        is Expr.Contst -> expr.number
        is Expr.Sum -> eva(expr.e1) + eva(expr.e2)
        Expr.NotANumber -> Double.NaN
    }
    //使用密封类的关键好处在于使用when表达式的时候，如果验证语句覆盖了所有情况就不需要为该语句添加else语句了


    //4.附加：
    //密封类就是专门来配合when语句使用的类，假如有一个view，通过when语句对view进行显示和隐藏操作
    sealed class Uiop {
        object Show : Uiop()
        object hide : Uiop()

        class TranslateX(val px: Float) : Uiop()
        class TranslateY(val px: Float) : Uiop()
    }

    fun execute(view: View, op: Uiop) = when (op) {
        Uiop.Show -> view.visibility = View.VISIBLE
        Uiop.hide -> view.visibility = View.GONE
        is Uiop.TranslateX -> view.translationX = op.px
        is Uiop.TranslateY -> view.translationY = op.px
        //这个when语句分支不仅告诉view要移动，还告诉view移动了多少距离
    }
    //以上操作完成可以用枚举实现，但是添加水平和纵向移动，枚举就不太好办了，密封类的操作就显示出来了


    //如果when语句不需要携带除“显示或隐藏view之外的其他信息时”(即只需要表明when语句分支，不需要携带额外数据时)
    //用object关键字创建就可以了，并且此时when字句不需要使用is关键字，只有需要携带额外信息时才定义密封子类，
    //而且使用了密封类就不需要使用else语句，每当我增加一个密封类子类或者单例，编译器就会在when中给出提示，
    //可以在编译阶段就给出错误。这是switch-else不具备的功能

    //封装一下这个函数
    // 先封装一个UI操作列表
    class Ui(val uiOps: List<Uiop> = emptyList()) {
        operator fun plus(uiop: Uiop) = Ui(uiOps + uiop)
    }

    //定义一组操作
    val ui = Ui() + Uiop.Show + Uiop.TranslateX(20f) + Uiop.TranslateY(40f) + Uiop.hide

    //定义调用函数
    fun run(view: View, ui: Ui) {
        ui.uiOps.forEach() { execute(view, it) }
    }

    //最终调用
//    run(view,ui)
}