DataCache
---------

一个Android数据保存工具，可同时保存至内存与硬盘缓存，以键值对的形式实现持久化保存，调用时先从内存读取，内存没有的话读取硬盘缓存。

code
----

可初始化配置：

 ```
 // 可设置缓存路径、目录、大小、版本号
 File cacheDir = getExternalCacheDir();
 DataCache.config().setCacheDir(cacheDir).setMaxSize(mb);
 ```

或直接使用：

 ```
 DataCache dataCache = DataCache.get(this);
 DataCache otherCache = DataCache.get(this, "test");
 
 // 设置字符串
 String str = "Hello world!";
 dataCache.putString(STRING, str);
 
 // 设置int
 otherCache.putInt(INT, 123);
 
 // 设置long
 dataCache.putLong(LONG, 20000l);
 
 // 设置double
 dataCache.putDouble(DOUBLE, 2.1d);
 
 // 设置float
 dataCache.putFloat(FLOAT, 2.2f);
 
 // 设置boolean
 dataCache.putBoolean(BOOLEAN, true);
 
 // 设置byte[]
 dataCache.putBytes(BYTE, str.getBytes());
 
 // 设置类
 People people = new People(21, "Dis");
 dataCache.putObject(OBJECT, people);
 
 // 设置集合
 List<String> items = new ArrayList<>();
 items.add("item1");
 items.add("item2");
 items.add("item3");
 items.add("item4");
 items.add("item5");
 dataCache.putObject(LIST, items);
 
 // 泛型设置
 People pp = new People(23, "Jo");
 dataCache.put("T", pp);
 
 // 异步设置
 dataCache.putStringAsync("Async", "asdfghjkl", new DataCache.Callback() {
     @Override
     public void finish() {
         String str = dataCache.getString("Async");
         textView.setText(str);
     }
 });
 ```
 
Using Android Studio
--------------------

编辑你的 build.gradle 文件，在dependency里面添加:

```
dependencies {
    compile 'jo.dis.datacache:library:1.2.1'
}
```

Developed By
------------
- Dis Jo - dis123520@gmail.com
 