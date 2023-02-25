✰✰✰✰✰
dialog  设置全屏
onCreateDialog()中->
dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
onCreate()中->
setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar);
//android.R.style.Theme_Black_NoTitleBarFULLSCREEN_FULLSCREEN这个不知道需不需要

✰✰✰✰✰
setLayout会影响window的大小


✰✰✰✰✰
多次对SpannableString 调用 setSpan时，将 span用CharacterStyle.wrap包裹起来  不能对同一个span使用多次setSpan,之前的setSpan会失效
spannable.setSpan(CharacterStyle.wrap(span), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

✰✰✰✰✰
kotlin文件中  Boolean::class.java 和 Boolean.javaClass的区别

✰✰✰✰✰
Boolean::class.java 对应kotlin的Boolean类  是KClass（继承java的Class）
Boolean.javaClass 对应java的Boolean类  是java的Class

✰✰✰✰✰
当反射一个kotlin的数据类的构造函数时 ，应该使用Boolean::class.java  ->   clazz.getDeclaredConstructor(Boolean::class.java)

✰✰✰✰✰
用URL访问本地文件 格式：
file:///storage/emulated/0/Android/data/com.duowan.mobile/files/yymobile/diamond_broadcast_svga/15499/SYHotballondiamondbroadcast.svga
注意是三条横线

✰✰✰✰✰
   关于translation 和 scroll  的理解
   translationX：相对于最初位置的x方向的偏移值
   translationY：相对于最初位置的y方向的偏移值
   
   正数向右移动 负数向左移动
   
   移动view内部的内容
   scrollTo  移动到制定的(x,y)位置
   scrollBy 实际上是调用了scrollTo(mScrollX + x, mScrollY + y)  当前位置在移动(x,y)个位置
   正数向左移动 负数向右移动
   
   想要制作跑马灯效果 必须使用scroll函数，使文字内容移动，不能用translation函数，它只会移动view的位置
 
✰✰✰✰✰
Fragment注意点：
 val mRootView = inflater.inflate(R.layout.fragment_vip_emotion, container ,false)   //必须是false

✰✰✰✰✰ 
inflater.inflate注意点
inflater.inflate(R.layout.item_vip_emotion_title, null) 时  item_vip_emotion_title布局设置的宽高就失效了 所以一般都要设置parentView 简单的：
inflater.inflate(R.layout.item_vip_emotion_title, FrameLayout(context))   

✰✰✰✰✰
spannable.setSpan(imageSpan) 注意设置drawable的大小
 it.setBounds(0, 0,40,40) //这里设置图片的大小

✰✰✰✰✰
InterceptFrameLayout 阻止事件向下传递，把事件传回给上层view的onTouchEvent

✰✰✰✰✰
kotlin 合并两个数组
val combined = Array.newInstance(Class.forName("类型"), oldArray1,length+oldArray2,length)
System.arraycopy(oldArray1,0,newArray,0,oldArray1,length)
System.arraycopy(oldArray2,0,newArray,oldArray1,length,oldArray2,length)

✰✰✰✰✰
初始化属性顺序
父类属性-> 父类构造方法-> 子类属性-> 子类构造方法
★★★ 子类如果重写 父类构造方法中调用的方法，父类构造过程中会进入子类的该重写方法，此时子类属性还没有初始化，会出现属性为null的情况。

✰✰✰✰✰
正则匹配
不以 mp4 结尾  ->    .*(?<!mp4)$
两个TAG过滤日志  ->   (TAG1)+|(TAG2)+
正则匹配  $05  ->  \\$\\d{2}

 
✰✰✰✰✰ Java和Kotlin交互注意到：
kotlin的Long::class.java 对应的Java的long.class ，而不是Long.class。
Java和Kotlin交互期间 使用泛型注意 kotlin使用Long::class.java时，Java代码必须进行long.class到Long.class转换
可以用Gson库的Primitives.wrap()方法进行long.class到Long.class转换

✰✰✰✰✰  内部类的泛型在编译期就确定下来了
错误写法：
    public static <K, V> Map<K, V> parseJsonMap(String json, Class<K> keyType, Class<V> valueType) {
        try {
            Type type = new TypeToken<Map<K, V>>() {
            }.getType();
            Map<K, V> result = new Gson().fromJson(json,
                    type);
            return result;
        } catch (Throwable throwable) {
        }
        return new HashMap<K, V>();
    }
这样子 内部类的Type 的泛型是<K,V> ,而不是真正的泛型类，因为内部类的泛型在编译期就确定下来了，而K，V泛型在运行期才确定了

正确写法：
    public static <K, V> Map<K, V> parseJsonMap(String json, Class<K> keyType, Class<V> valueType) {
        try {
            Primitives.wrap(keyType);
            Type type = TypeToken.getParameterized(Map.class, Primitives.wrap(keyType),
                    Primitives.wrap(valueType))
                    .getType();
            Map<K, V> result = gson.fromJson(json, type);
            return result;
        } catch (Throwable throwable) {
            MLog.error(TAG, throwable);
        }
        return new HashMap<K, V>();
    }
    
✰✰✰✰✰ 泛型的强转不会报错 （实际上并没有强转，只是声明变量属于该泛型）
    public static <K, V> Map<K, V> test(String json, Class<K> keyType, Class<V> valueType) {
        HashMap<K, V> kvHashMap = new HashMap<>();
        K k = (K) "ddf";
        V v = (V) new MainActivity();
        kvHashMap.put(k, v);
        return kvHashMap;
    }
这样子操作是没问题的，只是将MainActivity声明为V类型，代码编写期间可以将它放进HashMap。但在使用HashMap保存的值时，例如就hashmap.get(0)
会进行强转,可能发生强转崩溃

✰✰✰✰✰ 获得泛型 foo为内部类
Type mySuperClass = foo.getClass().getGenericSuperclass();
Type type = ((ParameterizedType)mySuperClass).getActualTypeArguments()[0];
System.out.println(type);

✰✰✰✰✰  Kotlin forEach中的break写法
        run loop@{
            x.forEach {
                if (y) {
                    return@loop
                }
            }
        }

✰✰✰✰✰ 多个wrap_content的View,如果避免被顶出父布局外？
   ConstraintLayout 采用match_parent布局：
       采用ConstraintLayout 的chain链 并使用 app:layout_constrainedWidth="true"属性 
       多个wrap_content的View的相对位置会影响View的显示效果
       先确定绝对位置固定死的View的占位，再把wrap_content的Views从ConstraintLayout最后一个View到第一个View逐渐开始测量占位
   ConstraintLayout 采用wrap_content布局，并有最大高度：
     
   
   
  
   
✰✰✰✰✰ ViewGroup想用用max_width属性 可以用 ConstraintLayout                            

✰✰✰✰✰ Rxjava 没有调用subscribeOn或者observeOn的话，都是在主线程进行

✰✰✰✰✰ 
父类泛型对象可以赋值给子类泛型对象，用 in
子类泛型对象可以赋值给父类泛型对象，用 out (java 的extend)
 
✰✰✰✰✰ LayoutInflater.from(context).inflate返回值   
 如果提供了root 且 attachToRoot为true，返回值是root， 否则，是xml 中的 root  
 
 
✰✰✰✰✰ Kotlin反射
方法1：
    val A = XXX::class.java.getDeclaredMethod(
        "name",XXX::class.java,
        XXX::class.java,
        XXX::class.java,
        XXX::class.java,
        XXX::class.java) 
    A.invoke(obj, x, x, x, x, x) 
    
方法2：
    val method = TrueLoveImpl::methodName
    method.call(obj,x,x,x,x,x,x)
    
✰✰✰✰✰ 当weditor找不到应该出现，没有被gone的View时。可能是xml默认配置导致该view 已经被顶到父View外部    


✰✰✰✰✰Glide小知识
Glide 的into方法必须在主线程调用 否则会抛异常 new IllegalArgumentException("You must call this method on the main thread");


✰✰✰✰✰ View.width没有及时更新
当View一直在界面中，当view由VISIBLE 变成Gone时，即使在View.post方法中 view.width也还是原来的数值，没有及时更新
需要用view.measuredWidth才能获得正常数据
原因可能是因为一直在界面上，post因为由attachInfo ,所以马上更新了，没来得及重新绘制


✰✰✰✰✰  一定不要使用==运算符检测两个字符串是否相等 用equal 或者 TextUtils.equals


✰✰✰✰✰ xml 中width=0 ,height=0, 或者超出父View的默认不进行绘制，除非代码手动修改尺寸

✰✰✰✰✰ StaticLayout
	/**
	 * @param source    需要分行的字符串
	 * @param bufstart  需要分行的字符串从第几的位置开始
	 * @param bufend    需要分行的字符串到哪里结束
	 * @param paint     画笔对象
	 * @param outerwidth  layout的宽度，超出时换行
	 * @param align       layout的对其方式，有ALIGN_CENTER， ALIGN_NORMAL， ALIGN_OPPOSITE 三种
	 * @param spacingmult 相对行间距，1.5f表示行间距为1.5倍的字体高度行间距的倍数，通常情况下填 1 就好。
	 * @param spacingadd  行间距的额外增加值，通常情况下填 0 就好
	 * @param includepad  是否在文本上下添加额外的空间，来避免某些过高的字符的绘制出现越界
	 * @param ellipsize  从什么位置开始省略
	 * @param ellipsizedWidth   超过多少开始省略
	 */

测量span的宽度：
        StaticLayout staticLayout = new StaticLayout(
                text,
                textPaint,
                Integer.MAX_VALUE,
                Layout.Alignment.ALIGN_NORMAL,
                1.0f,
                0.0f,
                false
        );
int width = staticLayout.getLineWidth(0)


✰✰✰✰✰ view的background的图如何不铺满整个View
✰✰✰✰✰ 设置有margin的backgroundDrawable
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
    <item>
        <shape>
            <solid android:color="#ffffff" />
        </shape>
    </item>
    <item
        android:bottom="xdp"
        android:top="xdp">
        <shape android:shape="rectangle">
            <solid android:color="#ffffff" />
            <corners android:radius="xdp" />
        </shape>
    </item>
</layer-list>

✰✰✰✰✰ 绘制带有空洞的Bitmap
    paint.isFilterBitmap = false
    val saveLayerId = canvas.saveLayer(0f, 0f, screenWidth, screenHeight,paint)
    val maskBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ALPHA_8)
    canvas.drawBitmap(maskBitmap, 0f, 0f, paint)
    mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    val holeBitmap = Bitmap.createBitmap(screenWidth, screenHeight, Bitmap.Config.ALPHA_8)
    canvas.drawBitmap(holeBitmap, 0f, 0f, paint)
    mPaint.xfermode = null
    canvas.restoreToCount(saveLayerId)
    
    
✰✰✰✰✰invalidate 没有调用 onDraw方法
1.将逻辑移动到 dispatchDraw
2.setWillNotDraw(false)


✰✰✰✰✰ 获得某个高度中 文字的BaseLine的Y坐标  @see raw/getFontMetrics.webp
    private fun getDrawTextBaseY(lineHeight: Float, paint: Paint): Float {
        val fontMetrics = paint.getFontMetrics()
        val distance = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom
        val baseline = lineHeight / 2 + distance
        return baseline
    }
    
✰✰✰✰✰ canvas 画文字 居中效果
canvas.drawText(str, x, getDrawTextBaseY(30dp, paint),paint)


✰✰✰✰✰ canvas.drawArc 画圆弧
drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter,Paint paint)

oval：为确定圆弧区域的矩形，圆弧的中心点为矩形的中心点
startAngle：为圆弧的开始角度（时钟3点的方向为0度，顺时钟方向为正）
sweepAngle：为圆弧的扫过角度（正数为顺时钟方向，负数为逆时钟方向）
useCenter：表示绘制的圆弧是否与中心点连接成闭合区域
paint：paint为绘制圆弧的画笔

✰✰✰✰✰ 圆弧渐变
SweepGradient(float cx, float cy, @NonNull @ColorInt int[] colors, @Nullable float[] positions)
默认0度是时钟3点的方向,若想从时钟0点开始绘画,方法:
val matrix = Matrix()
matrix.setRotate(-90f, width / 2f, height / 2f)
SweepGradient.setLocalMatrix(matrix);


✰✰✰✰✰ LinearGradient构造参数 LinearGradient(float x0, float y0, float x1, float y1......)
✰✰✰✰✰ 参数坐标都是对应画布的坐标 所以正确使用方式如下
canvas?.drawRoundRect(
    RectF(curX, curY, curX+width, curY+height), height / 2, height / 2, Paint().apply {
        isAntiAlias = true
        style = Paint.Style.FILL
        shader = LinearGradient(curX, 0f, curX+width, 0f, frameBgGradientColorArr, null, Shader.TileMode.CLAMP)
})
✰✰✰✰✰ 如果画从左到右的渐变 不用在意y0,y1的值 直接设置为0就可以了 设置为0和设置为10000效果是一样的


✰✰✰✰✰  ConstraintLayout marginLeft 不支持负数 --> 使用 translationX 属性

✰✰✰✰✰  倒计时控件 使用固定尺寸 避免频繁触发requestLayout

✰✰✰✰✰
getX()与getY()方法获取的是View左上角相对于父容器的坐标，当View没有发生平移操作时，getX()==getLeft()、getY==getTop()。
translationX与 translationY是View左上角相对于父容器的偏移量：translationX = getX() - getLeft(),当View未发生平移操作时，translationX 与translationY都为0


✰✰✰✰✰  PopupWindow
setBackgroundDrawable(ColorDrawable(0x00000000))  背景透明
当设置背景时， mBackgroundView 的宽是 MATCH_PARENT 高是mContentView的高，只能是WRAP_CONTENT或者MATCH_PARENT，
mContentView的高是具体值时，是MATCH_PARENT

当设置背景时， mContentView 的宽是 强制设成MATCH_PARENT 高只能是WRAP_CONTENT或者MATCH_PARENT，
mContentView的高是具体值时，强制改为MATCH_PARENT


