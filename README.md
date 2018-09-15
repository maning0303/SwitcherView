# SwitcherView
[![](https://jitpack.io/v/maning0303/SwitcherView.svg)](https://jitpack.io/#maning0303/SwitcherView)
垂直滚动的广告栏文字展示

## 屏幕截图：
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
	      compile 'com.github.maning0303:SwitcherView:v1.0.1'
	}
```

##使用方式
#### 1.布局文件添加
``` java
      xmlns:app="http://schemas.android.com/apk/res-auto"

      <com.maning.library.SwitcherView
        android:id="@+id/switcherView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:background="#ff99ff"
        app:switcherRollingTime="3000"
        app:switcherTextColor="#FF0000"
        app:switcherTextSize="14sp" />
          
      <---------------------------自定义参数介绍-------------------------------->
      <declare-styleable name="SwitcherView">
        <attr name="switcherTextColor" format="reference|color"/>       //文字的颜色
        <attr name="switcherTextSize" format="dimension"/>              //文字的大小
        <attr name="switcherRollingTime" format="integer"/>             //文字滚动的时间间隔
      </declare-styleable>
```
   
#### 2.设置数据源
``` java
        SwitcherView switcherView = (SwitcherView) findViewById(R.id.switcherView);

        ArrayList<String> strs = new ArrayList<>();
        strs.add("双十二购物节1");
        strs.add("双十二购物节2");
        strs.add("双十二购物节3");
        strs.add("双十二购物节4");
        strs.add("双十二购物节5");
        
        //设置-修改数据源
        switcherView.setResource(strs);
        //开始滚动
        switcherView.startRolling();
        //暂停滚动
        switcherView.stopRolling();
        //手动滚动到下一个
        switcherView.rollingToNext();

        //设置出入动画
        switcherView.setInAnimation(R.anim.anim_custom_in);
        switcherView.setOutAnimation(R.anim.anim_custom_out);

        //提供四个方向动画；默认从下往上
        switcherView.setAnimationTop2Bottom();
        switcherView.setAnimationBottom2Top();
        switcherView.setAnimationLeft2Right();
        switcherView.setAnimationRight2Left();


        //监听点击事件
        switcherView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	//获取当前的展示的值
                Toast.makeText(MainActivity.this, switcherView.getCurrentItem(), Toast.LENGTH_SHORT).show();
                //获取当前展示的集合的index
                Toast.makeText(MainActivity.this, switcherView.getCurrentItem(), Toast.LENGTH_SHORT).show();
            }
        });
```

#### 3.销毁View
``` java
    @Override
    protected void onDestroy() {
        switcherView.destroySwitcher();
        super.onDestroy();
    }
```

## 推荐:
Name | Describe |
--- | --- |
[GankMM](https://github.com/maning0303/GankMM) | （Material Design & MVP & Retrofit + OKHttp & RecyclerView ...）Gank.io Android客户端：每天一张美女图片，一个视频短片，若干Android，iOS等程序干货，周一到周五每天更新，数据全部由 干货集中营 提供。 |
[MNUpdateAPK](https://github.com/maning0303/MNUpdateAPK) | Android APK 版本更新的下载和安装,适配7.0,简单方便。 |
[MNImageBrowser](https://github.com/maning0303/MNImageBrowser) | 交互特效的图片浏览框架,微信向下滑动动态关闭 |
[MNCalendar](https://github.com/maning0303/MNCalendar) | 简单的日历控件练习，水平方向日历支持手势滑动切换，跳转月份；垂直方向日历选取区间范围。 |
[MClearEditText](https://github.com/maning0303/MClearEditText) | 带有删除功能的EditText |
[MNCrashMonitor](https://github.com/maning0303/MNCrashMonitor) | Debug监听程序崩溃日志,展示崩溃日志列表，方便自己平时调试。 |
[MNProgressHUD](https://github.com/maning0303/MNProgressHUD) | MNProgressHUD是对常用的自定义弹框封装,加载ProgressDialog,状态显示的StatusDialog和自定义Toast,支持背景颜色,圆角,边框和文字的自定义。 |
[MNXUtilsDB](https://github.com/maning0303/MNXUtilsDB) | xUtils3 数据库模块单独抽取出来，方便使用。 |
[MNVideoPlayer](https://github.com/maning0303/MNVideoPlayer) | SurfaceView + MediaPlayer 实现的视频播放器，支持横竖屏切换，手势快进快退、调节音量，亮度等。------代码简单，新手可以看一看。 |
[MNZXingCode](https://github.com/maning0303/MNZXingCode) | 快速集成二维码扫描和生成二维码 |
[MNChangeSkin](https://github.com/maning0303/MNChangeSkin) | Android夜间模式，通过Theme实现 |
[SwitcherView](https://github.com/maning0303/SwitcherView) | 垂直滚动的广告栏文字展示。 |
