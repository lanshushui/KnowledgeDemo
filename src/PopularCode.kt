//动画 中心放大
val animatorSet = AnimatorSet()
val scaleX: ObjectAnimator = ObjectAnimator.ofFloat(rootView, "scaleX", 0f, 1f)
val scaleY: ObjectAnimator = ObjectAnimator.ofFloat(rootView, "scaleY", 0f, 1f)
rootView.setPivotX(rootView.width / 2f)
rootView.setPivotY(rootView.height / 2f)
animatorSet.duration = 2000
animatorSet.play(scaleX).with(scaleY)
animatorSet.start()

//设置Bitmap的尺寸
public Bitmap zoomImg(Bitmap bm, int newWidth, int newHeight) {
    // 获得图片的宽高
    int width = bm . getWidth ();
    int height = bm . getHeight ();
    // 计算缩放比例
    float scaleWidth =((float) newWidth) / width;
    float scaleHeight =((float) newHeight) / height;
    // 取得想要缩放的matrix参数
    Matrix matrix = new Matrix();
    matrix.postScale(scaleWidth, scaleHeight);
    // 得到新的图片
    Bitmap newbm = Bitmap . createBitmap (bm, 0, 0, width, height, matrix, true);
    return newbm;
}

//获得渐变的Drawable
val colors =
    intArrayOf(color1, color2)
val drawable = GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, colors)
drawable.gradientType = GradientDrawable.LINEAR_GRADIENT
view?.background = drawable



//callback转为协程
suspend fun getUserCoroutine() = suspendCancellableCoroutine<User> { continuation ->
    val call = OkHttpClient().newCall(...)

    continuation.invokeOnCancellation { // ①
        log("invokeOnCancellation: cancel the request.")
        call.cancel()
    }

    call.enqueue(object : okhttp3.Callback {
        override fun onFailure(call: Call, e: IOException) {
            log("onFailure: $e")
            continuation.resumeWithException(e)
        }

        override fun onResponse(call: Call, response: Response) {
            log("onResponse: ${response.code()}")
            response.body()?.let {
                try {
                    continuation.resume(User.from(it.string()))
                } catch (e: Exception) {
                    continuation.resumeWithException(e)
                }
            } ?: continuation.resumeWithException(NullPointerException("ResponseBody is null."))
        }
    })
}

//将 RecyclerView 滚动到底部的解决方法
fun scrollToBottom() {
    val adapter = adapter ?: return
    // scroll to last item to get the view of last item
    val layoutManager = layoutManager as LinearLayoutManager?
    val lastItemPosition = adapter.itemCount - 1
    layoutManager?.scrollToPositionWithOffset(lastItemPosition, 0)
    post { // then scroll to specific offset
        val target: View? = layoutManager?.findViewByPosition(lastItemPosition)
        if (target != null) {
            val offset: Int = measuredHeight - target.measuredHeight
            layoutManager.scrollToPositionWithOffset(lastItemPosition, offset)
        }
    }
}