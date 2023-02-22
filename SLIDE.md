
当一个列表中还需一个与外层列表滑动方向一致的列表时，当想滑动里层列表时，手势会默认被外层RecyclerView拦截处理，导致里层列表无法滑动

当拖动超过一定范围，onInterceptTouchEvent方法中 RecycleView把他当做了滑动列表的操作了
所以mScrollState设置为了1，onInterceptTouchEvent return 了true，自己吃掉了事件。
导致一个场景：当手指快速滑动超过一定距离时，子类View收到ACTION_DOWN和 ACTION_CANCEL 事件，无法收到ACTION_MOVE

parent.requestDisallowInterceptTouchEvent(true) 会一直向上透传  每次调用只在一次完整的滑动周期中生效
当子view move事件过程中返回requestDisallowInterceptTouchEvent(false)时，该操作不可逆，一旦父组件消费了事件，则再也不会传递下来了 一直被父控件消费


滑出view范围后，如果父view没有拦截事件，则会继续受到ACTION_MOVE和ACTION_UP等事件。
一旦滑出view范围，view会被移除PRESSED标记，这个是不可逆的，然后在ACTION_UP中不会执行performClick()等逻辑。


上下滑逻辑：
一个默认滑到中间位置，size为Int最大值的RecyclerView
在LayoutManager中重点处理onScrollStateChanged 和onChildViewAttachedToWindow 方法 

在onChildViewAttachedToWindow方法中判断是否第一次 是则将直播间View添加到item容器中
在onScrollStateChanged方法中判断是否是当前状态是SCROLL_STATE_IDLE且之前状态是SCROLL_STATE_SETTLING
是则通过findSnapView方法找到当前item容器，将直播间View添加到当前容器中

通过自定义PagerSnapHelper子类  findTargetSnapPosition方法（快速滑动回调）和findSnapView方法（慢速滑动回调）来实现对上下滑的拦截



MotionEvent
getX()和getY()：由这两个函数获得的x,y值是相对的坐标值，相对于消费这个事件的视图的左上点的坐标。

其中ViewGroup的dispatchTransformedTouchEvent函数有如下一段代码:
    final float offsetX = mScrollX - child.mLeft;
    final float offsetY = mScrollY - child.mTop;
    event.offsetLocation(offsetX, offsetY); //该行可以看出相关坐标是如何改变的
    handled = child.dispatchTouchEvent(event);
    event.offsetLocation(-offsetX, -offsetY);


