# jni-library-loader

JNI加载动态类库

## 使用方法

```groovy

```

```java
import com.xiaohaoo.jniloader.JniLoader;

class Main {
    private static native void nativeMethod();

    public static void main(String[] args) {
        JniLoader.loadLibrary("opencv.dylib");
        Main.nativeMethod();
    }
}
```
