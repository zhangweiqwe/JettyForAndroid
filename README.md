# AndroidWebServer
AndroidWebServer base on jetty

If you have questions, please contact zh857899299@gmail.com or 857899299@qq.com



#### Jetty
|默认服务|webServer开启|
|:---:|:---:|
|![](https://raw.githubusercontent.com/zhangweiqwe/AndroidWebServer/master/1.png)|![](https://raw.githubusercontent.com/zhangweiqwe/AndroidWebServer/master/2.png)|

|数据库user表|添加数据|
|:---:|:---:|
|![](https://raw.githubusercontent.com/zhangweiqwe/AndroidWebServer/master/3.png)|![](https://raw.githubusercontent.com/zhangweiqwe/AndroidWebServer/master/4.png)|

|other|更新某行数据|
|:---:|:---:|
|![](https://raw.githubusercontent.com/zhangweiqwe/AndroidWebServer/master/5.png)|![](https://raw.githubusercontent.com/zhangweiqwe/AndroidWebServer/master/6.png)|

|查看数据表|自定义查询语句|
|:---:|:---:|
|![](https://raw.githubusercontent.com/zhangweiqwe/AndroidWebServer/master/7.png)|![](https://raw.githubusercontent.com/zhangweiqwe/AndroidWebServer/master/8.png)|


http://www.wsgwz.cn


## 已知的问题 ##

  * 由于android.jar 缺少javax and java.lang.* 及其他包下的很大部分java支持，不能完整运行jsp
  * 数据库使用room，底层也是sqlite，未使用contentProvider，并不知道多进程安全性，请不要将webServer单独放在独立的进程里
  * 设计用户管理系统的时候疏忽了客户端token过期的ResultCode，请检查Result枚举并添加

  
  ## 当项目过大，可能面临的问题 ##

  * 多进程，sharedpreferences 会失效，room不清楚多进程安全性，room query不建议在主线程中运行，虽然一般在servlet线程使用，但主线程中会有许多回调。这样保存配置文件或许只能用File或ObjectOutputStream保存,两者本质是一样的
  * 多进程会导致单列懒汉模式多次初始化，包括其他static 变量

## 其他说明 ##
  * 这个项目我做了六天左右，最初的想法是做个自己app的服务器，当然这事实上也是可以实现的。但是，移动宽带没有外网ip，也申请不到，这让我觉得做这个事情没有什么成功，和我出发点不一样了。还有最主要的是，我还有很多其他的事做，希望大家取舍适中，不要上我的当了。关于文中的注释都不是我写的，我一般不写注释，这些项目比较小，没有值得说明的地方。

</br>

<a></a>

<a name="FAQ0"></a>
[**About Jetty**](http://www.eclipse.org/jetty/)
</br>
[**About SqliteManager**](https://github.com/Ashok-Varma/SqliteManager)
