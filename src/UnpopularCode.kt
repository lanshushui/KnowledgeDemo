//textview 播放gif
Glide.with(this)
.asGif()
.load(url)
.into(object : SimpleTarget<GifDrawable>() {
    override fun onResourceReady(
        resource: GifDrawable,
        transition: Transition<in GifDrawable>?
    ) {
        resource.setBounds(0, 0, 50, 50)
        val imageSpan = ImageSpan(resource)
        val span = SpannableString("x")
        span.setSpan(
            imageSpan,
            0,
            span.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        mTilte.setText(span)
        resource.start()
        resource.setLoopCount(Int.MAX_VALUE)
        resource.callback = object : Drawable.Callback {
            override fun invalidateDrawable(who: Drawable) {
                mTilte.invalidate()
            }

            override fun scheduleDrawable(who: Drawable, what: Runnable, `when`: Long) {
                mTilte.invalidate()
            }

            override fun unscheduleDrawable(who: Drawable, what: Runnable) {
                mTilte.invalidate()
            }
        }
    }
})

//检测fps
class Metronome(var interval: Int = 500, var listener: Audience) : Choreographer.FrameCallback {
    private val choreographer: Choreographer

    private var frameStartTime: Long = 0
    private var framesRendered = 0

    init {
        choreographer = Choreographer.getInstance()
    }

    fun start() {
        choreographer.postFrameCallback(this)
    }

    fun stop() {
        frameStartTime = 0
        framesRendered = 0
        choreographer.removeFrameCallback(this)
    }

    override fun doFrame(frameTimeNanos: Long) {
        val currentTimeMillis = TimeUnit.NANOSECONDS.toMillis(frameTimeNanos)

        if (frameStartTime > 0) {
            // take the span in milliseconds
            val timeSpan = currentTimeMillis - frameStartTime
            framesRendered++
            if (timeSpan > interval) {
                val fps = framesRendered * 1000 / timeSpan.toDouble()
                frameStartTime = currentTimeMillis
                framesRendered = 0
                listener.heartbeat(fps)
            }
        } else {
            frameStartTime = currentTimeMillis
        }

        choreographer.postFrameCallback(this)
    }

    interface Audience {
        fun heartbeat(fps: Double)
    }
}

//反射合并两个数组
private Object combineArray(Object firstArr, Object secondArr) {
    int firstLength = Array . getLength (firstArr);
    int secondLength = Array . getLength (secondArr);
    int length = firstLength +secondLength;
    Class<?> componentType = firstArr . getClass ().getComponentType();
    Object newArr = Array . newInstance (componentType, length);
    for (int i = 0; i < length; i++) {
        if (i < firstLength) {
            Array.set(newArr, i, Array.get(firstArr, i));
        } else {
            Array.set(newArr, i, Array.get(secondArr, i - firstLength));
        }
    }
    return newArr;
}


//设置图片的透明度从左到右渐变
private fun getTransAlphaBitmap(sourceImg: Bitmap, startA: Float, endA: Float): Bitmap {
    val width = sourceImg.width
    val height = sourceImg.height
    val bitArr = IntArray(width * height)
    sourceImg.getPixels(bitArr, 0, width, 0, 0, width, height)
    val rangeA = endA - startA
    return try {
        for (i in 0 until width) {
            val progress: Float = i.toFloat() / width.toFloat()
            val alpha = (startA + rangeA * progress) * 255
            for (j in 0 until height) {
                val index = j * width + i
                val originAlpha = bitArr[index] ushr 24
                val mixAlpha = originAlpha * alpha / 255
                bitArr[index] =
                    mixAlpha.toInt() shl 24 or (bitArr[index] and 0x00FFFFFF)
            }
        }
        Bitmap.createBitmap(bitArr, width, height, Bitmap.Config.ARGB_8888)
    } catch (e: Throwable) {
        sourceImg
    }
}