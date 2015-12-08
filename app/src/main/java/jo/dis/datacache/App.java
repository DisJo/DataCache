package jo.dis.datacache;

import android.app.Application;
import android.content.Context;
import android.os.Environment;

import java.io.File;

import jo.dis.library.data.DataCache;

/**
 * Created by Dis on 15/12/3.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        File file = getDiskCacheDir(this, "AppCache");
        if (!file.exists())
            file.mkdirs();
        // 初始化数据缓存
        DataCache.init(file.toString(), 10 * 1024);
    }

    private File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            // /sdcard/Android/data/<application package>/cache
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            // /data/data/<application package>/cache
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
}
