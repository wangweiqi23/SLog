# SLog

```
    Android log日志库 －功能齐全，扩展性强，性能高效。（只需一次初始化，就可以随心处理log）
```

<img src='https://travis-ci.org/elvishew/xLog.svg?branch=master'/>

---

#### 最新版本

模块|Demo apk
---|---
最新版本|[Download](https://github.com/wangweiqi23/SLog/blob/master/image/app-debug.apk)

#### Demo展示

##### [去看看更多效果图](https://github.com/wangweiqi23/SLog/blob/master/image)

<img src='https://github.com/wangweiqi23/SLog/blob/master/image/JSON.png'/>

#### 一、功能介绍
1. **支持多个通道打印日志，可自定义实现打印机**
2. **支持同步、异步方式写日志到文件**
3. **支持日志缓存自动清理**
4. **多种多样的格式化工具**
5. **支持多种文件，满足实际应用中多种类型日志需求（时间片、文件后缀）**
6. **支持打印无限长的日志**
7. **支持线程信息输出**
8. **支持调用栈信息（可配置的调用栈深度）**
8. **支持多种TAG类型配置组合**

#### 二、典型应用
1. 调试时，在Android Studio Logcat中输出
2. 保存应用中部分重要日志到外部储存。

#### 三、基础功能
1. 添加依赖和配置
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
    
dependencies {
	compile 'com.github.wangweiqi23:SLog:v1.0.0'
}
```

2. 初始化
``` java
@Override
    public void onCreate() {
        super.onCreate();
        Context context = getApplicationContext();

        //init log
        Settings settings = new Settings.Builder()
                .context(context)//获取设备信息等写到日志文件头部
                .mLogSegment(LogSegment.TWENTY_FOUR_HOURS)//保存日志文件时间切片 如果缓存日志量大可以使用小时间片
                .zoneOffset(ZoneOffset.P0800)//保存日志时区偏移
                .timeFormat(SLogConstants.DEFAULT_TIME_FORMAT)//保存日志时间格式
                .isBorder(true)//是否 开启外框
                .isThread(true)//是否 打印线程信息
                .isStackTrace(true)//是否 打印堆栈跟踪信息 非必要可以关闭 提升性能
                .build();

        /**
         * 创建一个控制台打印机
         */
        Printer consolePrinter = new DefaultConsolePrinter();
        /**
         * 设置需要的堆栈跟踪信息深度为2层并开启数据自动JSON格式化
         */
        consolePrinter.setFormatter(new DefaultConsoleFormatter(3, true));

        /**
         * 创建一个SD卡文件打印机 设置日志存储地址
         * 默认开启 一个文件上限为30的缓存清理工具 你也可以null关闭 或者自定义实现
         */
        Printer filePrinter = new DefaultFilePrinter(SDUtils.getLogPath(getApplicationContext()));
        filePrinter.setFormatter(new DefaultFileFormatter());
        filePrinter.addLevelForFile(new ArrayList<LogLevel>() {//需要写入文件的日志类型 不设置默认全写入日志文件
            {
                add(LogLevel.WTF);
            }
        });

        if (BuildConfig.DEBUG) {
            SLog.init(settings, consolePrinter, filePrinter);
        } else {
            SLog.init(settings, filePrinter);//非debug环境 关闭consolePrinter
        }

    }
```


3. 打印日志
``` java
// 1. 应用内简单的应用
SLog.d(TAG, "测试");

// 2. 同步方式将 crash日志单独写入特定文件中
使用同步方式保证进程被杀之前保存好信息
正常场景推荐 异步方式
SLog.wtfParams(TAG, "catch a crash info", "carsh", true);
```

4. 添加混淆规则(如果使用了Proguard)
``` 
-keepattributes SourceFile, LineNumberTable
-keep class com.weiqi.slog.** { *; }
```

#### 四、进阶用法
1. 自定义文件打印机
``` java
跟sdk中的 DefaultFilePrinter一样实现Printer接口
你可以同步或者异步去写日志
也可以自定义日志的包装格式

最后，将打印机放入SLog 
SLog.init(settings, filePrinter);
```

2. 自定义格式化工具
``` java
如果你觉得内置的格式化工具不能满足你 你可以自定义格式化工具
跟sdk中 DefaultFileFormatter一样实现MessageFormatter接口
组织包装你需要的日志格式

最后，将打印机放入对应的打印机
filePrinter.setFormatter(new DefaultFileFormatter());
```

3. 自定义日志缓存清理工具
``` java
sdk中自带的LogCacheHelper是按时间顺序清理缓存文件的，如果觉得不适用，也可以自己实现。
最后放入对应打印机
DefaultFilePrinter(String logDir, CacheHelper cacheHelper)
```

#### 五、功能&性能测试

参见demo中MainActivity各项测试


#### 六、其他

1. 沟通和交流

    我的简书：http://www.jianshu.com/users/884c2e4b6e38/timeline
    
