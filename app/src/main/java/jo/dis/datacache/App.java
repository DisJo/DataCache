package jo.dis.datacache;

import android.app.Application;

import java.io.File;

import jo.dis.library.data.DataCache;

/**
 * Created by Dis on 15/12/3.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        File cacheDir = getExternalCacheDir();
        int mb = 1000 * 1000 * 10;
        DataCache.config().setCacheDir(cacheDir).setMaxSize(mb);
    }
}
