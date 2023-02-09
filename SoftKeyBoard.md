✰✰✰✰✰
针对Dialog 软键盘 的分析

dialog.window?.setLayout，dialog.window?.setSoftInputMode  都是设置DecorView的属性
提供给dialog 的父布局的高度一定会被设置成match_parent
DecorView           setSoftInputMode是OFT_INPUT_ADJUST_PAN时  ,找到焦点View位置，并移动自己的画布  
-------LinearLayout   高度是 match_parent    setSoftInputMode是SOFT_INPUT_ADJUST_RESIZE时 会设置该view的paddingButtom 调整布局空出位置
----------------FrameLayout 高度是 match_parent
--------------------------- 提供的父布局  该布局的高度一定会被设置成 match_parent

设置SOFT_INPUT_ADJUST_RESIZE时，不会改变原来布局的高度 ，通过设置paddingButtom ，让布局内容上移，不能将整个布局上移 。  
所以setLayout的高不能设置成Wrap_content，这样子可能导致整个布局过小

设置SOFT_INPUT_ADJUST_PAN时，不会改变原来布局的高度 ，通过绘制画布，让布局内容上移，不能将整个布局上移 。
所以setLayout的高不能设置成Wrap_content，这样子可能导致整个布局过小


按理setLayout的高设置成Wrap_content，采用SOFT_INPUT_ADJUST_RESIZE方式会设置子布局LinearLayout的paddingBottom
按照一般xml的逻辑 重新绘制布局会增加DecorView的高度
但实际上DecorView的高度不会发生变化，可能是整个window的高度没有发生改变导致DecorView的高度不会发生变化


✰✰✰✰✰软键盘处理方案

onCreateDialog --方法中
    //不让Activity做出反应
    activity?.window?.setSoftInputMode(WindowManager .LayoutParams.SOFT_INPUT_ADJUST_NOTHING)
    dialog.window?.let {
        //设置在底部
        it.setGravity(Gravity.BOTTOM)
        it.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        it.decorView.setPadding(0, 0, 0, 0)
        //固定高度，因为目前没有找到SOFT_INPUT_ADJUST_RESIZE 和Wrap_content 和谐共处的方式
        it.setLayout(WindowManager.LayoutParams.MATCH_PARENT,(ScreenUtil.getRealHight(activity) * 0.75).toInt())
    }
//上面适用安卓9以下机型，无法做到丝滑效果
//下面适用安卓9以上机型，做到丝滑效果
onCreateView -- 方法中
    //通知window inset操作由我们自己处理 该方法传入false后会忽略SOFT_INPUT_ADJUST_RESIZE参数
    WindowCompat.setDecorFitsSystemWindows(window, false)
    //设置windowinsets监听回调
    ViewCompat.setWindowInsetsAnimationCallback(//软键盘占位View, WindowInsetsAnimationCompat.Callback(DISPATCH_MODE_STOP))

WindowInsetsAnimationCompat.Callback 
    ---------onProgress 方法
        // 虚拟导航栏的高度
        val navigationBars = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
        navigationBarsHeight = navigationBars.bottom
        //键盘升起过程中的高度
        val typesInset = insets.getInsets(WindowInsetsCompat.Type.ime())
        lastBottomMargin = typesInset.bottom
        //设置占位View的marginBottom

