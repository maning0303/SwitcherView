# SwitcherView
垂直滚动的广告栏文字展示

###屏幕截图：
![](https://github.com/maning0303/SwitcherView/raw/master/screenshots/001.gif)

###代码展示：
      <declare-styleable name="SwitcherView">
          <attr name="switcherTextColor" format="reference|color"/>   //文字的颜色
          <attr name="switcherTextSize" format="dimension"/>          //文字大小
          <attr name="switcherRollingTime" format="integer"/>         //文字滚动的时间s
          <attr name="switcherDefaultText" format="string"/>          //默认展示的文字
      </declare-styleable>

      <com.maning.library.SwitcherView
          android:id="@+id/switcherView"
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:background="#ff99ff"
          app:switcherDefaultText="默认展示的文案"
          app:switcherRollingTime="4000"
          app:switcherTextColor="@color/colorPrimaryDark"
          app:switcherTextSize="16sp" />
