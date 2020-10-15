package com.suming.kotlindemo.blog//包的声明
import android.os.Bundle
import android.widget.TextView//导入android.widget包下的TextView类
import androidx.appcompat.app.AppCompatActivity
import java.util.*//java.util包下的所有类可用
import kotlin.collections.ArrayList//ArrayList是java.util包下的的子包
import com.suming.kotlindemo.blog.TextView1 as MyView//MyView 表示com.suming.kotlindemo.blog.TextView

/**
 * @创建者 mingyan.su
 * @创建时间 2020/08/12 15:06
 * @类描述 {$TODO}导包
 */

fun getListData(){}

class ImportsPackagesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //(1)除了默认导入的包外，每个文件可能需要你使用的某些包：
        var textView = TextView(this)//这里需要用到android.widget包下的TextView类，需要在头部文件

        //(2)或者范围内所有可访问的内容（包，类，对象等）：导入包java.util.*表示包下的所有类都可以使用了，
        // 但是如果java.util.*包下还有其他子包，则需要导入具体子包才可使用子包下的类。

        var random = Random()//Random类在java.util包下
        var date = Date(1000)//Date类在java.util包下

        var list = ArrayList<String>()//ArrayList在kotlin.collections包下

        //(3)如果包名有名称冲突，我们可以使用 as 关键字本地重命名冲突实体类消除歧义。
        //下面的 android.widget.TextView 与 com.suming.kotlindemo.blog.TextView 包名重名了，
        //编译器会报错的，使用 as 关键字类重写命名，使用的时候以新命名的名字为准。
        var myView = MyView() //使用 as 关键字重新定义了com.suming.kotlindemo.blog.TextView，这里使用 MyView

        //(4)import关键字不限于导入类，也可以用来导入其他声明：
            //—顶级top-level函数和属性；
            //—对象函数声明的函数和属性；
            //—枚举常量。
    }

}