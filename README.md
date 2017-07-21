

# **StatusBarControl**
## 一个简单实用的状态栏工具，可以设置状态栏颜色，无论你是沉浸式还是透明状态栏都支持。
---
功能：

 - 可设定状态栏颜色
 - 可设定全屏且状态栏透明，内容延伸到状态栏下方
 

---
使用方法:

 - 代码操作
 

```java
//设置状态栏颜色
StatusBarControl.setStatusColor(this, getResources().getColor(android.R.color.holo_red_dark));

//设置全屏状态栏透明，内容延伸到状态栏下方，适用于图片情形
StatusBarControl.transparentStatusBar(this);

//设置全屏状态栏透明，内容延伸有阴影
StatusBarControl.transparentStatusBarWithShadow(this);

```

---

引入方法：

 - 在你的Project的 build.gradle 按下面的操作配置仓库。
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

 - 然后在你对应的Modlule内的build.gradle内按下面的方式进行引入。

	

```
dependencies {
     compile 'com.github.guohaiyang1992:WaveView:0.2'
	}
```
