# SwitcherView
[![](https://jitpack.io/v/maning0303/SwitcherView.svg)](https://jitpack.io/#maning0303/SwitcherView)
垂直滚动的广告栏文字展示

##屏幕截图：
![](https://github.com/maning0303/SwitcherView/raw/master/screenshot/001.gif)

## 如何添加

#### 1.在Project的build.gradle中添加仓库地址

``` gradle
	allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

#### 2.在Module目录下的build.gradle中添加依赖
``` gradle
	dependencies {
	      compile 'com.github.maning0303:SwitcherView:v1.0.0'
	}
```

##使用方式
#### 1.布局文件添加

      xmlns:app="http://schemas.android.com/apk/res-auto"

      <com.maning.library.SwitcherView
          android:id="@+id/switcherView"
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:background="#ff99ff"
          app:switcherDefaultText="默认展示的文案"
          app:switcherRollingTime="4000"
          app:switcherTextColor="@color/colorPrimaryDark"
          app:switcherTextSize="16sp" />
          
      <---------------------------自定义参数介绍-------------------------------->
      <declare-styleable name="SwitcherView">
        <attr name="switcherTextColor" format="reference|color"/>       //文字的颜色
        <attr name="switcherTextSize" format="dimension"/>              //文字的大小
        <attr name="switcherRollingTime" format="integer"/>             //文字滚动的时间间隔
        <attr name="switcherDefaultText" format="string"/>              //默认展示的文字
      </declare-styleable>

   
#### 2.设置数据源

        SwitcherView switcherView = (SwitcherView) findViewById(R.id.switcherView);

        ArrayList<String> strs = new ArrayList<>();
        strs.add("哎呦，不错哦");
        strs.add("你知道我是谁吗你知道我是谁吗你知道我是谁吗");
        strs.add("哈哈哈");
        strs.add("1111111111111");
        
        //设置数据源
        switcherView.setResource(strs);
        //开始滚动
        switcherView.startRolling();

        //监听获取当前的Item
        switcherView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, switcherView.getCurrentItem(), Toast.LENGTH_SHORT).show();
            }
        });

#### 3.销毁View
        switcherView.destroySwitcher();
