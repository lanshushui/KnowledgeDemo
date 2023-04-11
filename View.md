✰✰✰✰✰   TextView取消上下padding

android:includeFontPadding="false"

这个属性为true时，TextView的绘制区域为top至buttom。 为false时，TextView的绘制区域为ascent至decent。

✰✰✰✰✰   LinearLayout的特殊之处
父布局宽为wrap_content时，其中一个子view不想影响父布局的宽度，最宽为父布局的长度时，采用LinearLayout，该子view宽用match_parent
即父布局宽为wrap_content 子view宽用match_parent

✰✰✰✰✰  Fragment和父view
当父view 被removeFromParent ，不会影响Fragment的生命周期