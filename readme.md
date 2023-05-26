# DBApi插件开发案例

[DBApi](https://github.com/freakchick/DBApi) 插件开发案例

## 概述
> 随着DBApi的版本更新，插件也会随版本更新，请查看**对应版本**的插件demo

## [DBApi插件开发指南](https://www.51dbapi.com/v3.2.0/zh/plugin/)

## 使用方式
```xml
<dependency>
    <groupId>com.gitee.freakchicken.dbapi</groupId>
    <artifactId>dbapi-plugin</artifactId>
    <version>4.0.0</version>
    <scope>provided</scope>
</dependency>
```


## DBApi版本与dbapi-plugin版本对应关系

> 不同版本的DBApi使用的插件必须依赖相应版本的dbapi-plugin.jar，版本对应关系如下

| DBApi版本 | dbapi-plugin版本 |
| -------- | ----- |
| 2.3.1 | 2.3.1 |
| 2.3.2 | 2.3.2 |
| 3.0.0 | 3.0.0 |
| 3.1.1 - 3.2.0 | 3.1.1 |
| 4.0.0 | 4.0.0|

## 注意
> 从DBAPI 3.1.1版本开始，插件编写需要手动注册

在`resources`目录下新建文件夹`META-INF`,再在`META-INF`文件夹下新建`services` 文件夹

在`META-INF/services`目录下新建文件`com.gitee.freakchicken.dbapi.plugin.CachePlugin`，并在此文件中填写编写的缓存插件的java类名

在`META-INF/services`目录下新建文件`com.gitee.freakchicken.dbapi.plugin.TransformPlugin`，并在此文件中填写编写的数据转换插件的java类名

在`META-INF/services`目录下新建文件`com.gitee.freakchicken.dbapi.plugin.AlarmPlugin`，并在此文件中填写编写的告警插件的java类名

在`META-INF/services`目录下新建文件`com.gitee.freakchicken.dbapi.plugin.GlobalTransformPlugin`，并在此文件中填写编写的全局数据转换插件的java类名
