android:textDirection="ltr"
tools:text="مرحبًا"
-》 展示效果 为   مرحبًا

android:textDirection="ltr"
tools:text="مرحبًا"
                         -》 展示效果 为    مرحبًا

因为字符方向性的问题，所有字符都是同一个方向的情况下 ，没有textDirection 也能正常工作


android:textDirection="rlt"
tools:text="Hello , مرحبًا"
                         -》 展示效果 为    مرحبًا , Hello



android:textDirection="ltr"
tools:text="Hello..."
                        -》 展示效果 为    Hello...

android:textDirection="rlt"
tools:text="Hello..."
                         -》 展示效果 为    ...Hello

其他特殊符号同类 因为它们的字符方向性都是双向的，可以ltr 也可以是rtl





