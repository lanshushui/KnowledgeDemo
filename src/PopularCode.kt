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