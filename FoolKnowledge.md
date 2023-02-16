1.Kotlin 操作符不能在最前面
val width = 100 - 10
-20 - 30

导致的结果是 变成两行独立代码执行 （第二行代码没有意义）; 结果 width 是90

要改成
val width = 270 - 10 -20 - 30  
或者
val width = 100 - 10 -
20 -
30

才是正确操作

2.try catch 不能捕捉异步线程引发的崩溃
                                                         

3.kotlin设置变量只能读取不能修改
    var text: CharSequence? = null
        private set
   