沉浸式讨论

✰✰✰✰✰
SYSTEM_UI_FLAG_FULLSCREEN(4.1+)
隐藏状态栏，手指在屏幕顶部往下拖动，状态栏会再次出现且不会消失，
另外activity界面会重新调整大小，直观感觉就是activity高度有个变小的过程。

✰✰✰✰✰
SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN(4.1+)
配合SYSTEM_UI_FLAG_FULLSCREEN一起使用，效果使得状态栏出现的时候不会挤压activity高度，状态栏会覆盖在activity之上

✰✰✰✰✰
SYSTEM_UI_FLAG_HIDE_NAVIGATION(4.0+)
会使得虚拟导航栏隐藏，但同样用户可以从屏幕下边缘“拖出”且不会再次消失，同时activity界面会被挤压

✰✰✰✰✰
SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION(4.1+)
配合 SYSTEM_UI_FLAG_HIDE_NAVIGATION 一起使用，
效果使得导航栏出现的时候不会挤压activity高度，导航栏会覆盖在activity之上。

使用以上四个属性，可以达到activity占据屏幕所有空间，同时状态栏和导航栏可以悬浮在activity之上的效果

✰✰✰✰✰
android:fitsSystemWindows=“true”
这个属性表示系统UI（状态栏、导航栏）可见的时候，会给我们的布局加上padding（paddingTop、paddingBottom）属性，
这样内容就不会被盖住了


以上都是4.1(除了SYSTEM_UI_FLAG_HIDE_NAVIGATION)的属性，
观察之后我们发现，不管是那种属性，状态栏和导航栏总是会“遮挡”activity，为了解决这个问题，4.4引入了“全屏沉浸模式”这个概念。

✰✰✰✰✰
SYSTEM_UI_FLAG_IMMERSIVE(4.4+)：这个属性是用来实现“沉浸式”效果的

✰✰✰✰✰
SYSTEM_UI_FLAG_IMMERSIVE_STICKY 
和SYSTEM_UI_FLAG_IMMERSIVE相似，它被称作“粘性”的沉浸模式，这个模式会在状态栏和导航栏显示一段时间后，自动隐藏


✰✰✰✰✰ 上面花里胡哨   直接使用 ImmersionBar库



