package jo.dis.library.data;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import jo.dis.library.data.cache.DiskCache;
import jo.dis.library.data.cache.MemoryCache;

/**
 * Created by Dis on 15/12/1.
 */
public class DataCache {

    private static final String CACHE_NAME = "datacache";
    private static final int MAX_SIZE = 1000 * 1000 * 30;   // 30mb
    private static final int APP_VERSION = 1;
    private static Map<String, DataCache> mInstanceMap = new HashMap<>();
    private MemoryCache memoryCache = null;
    private DiskCache diskCache = null;
    private static Config config = new Config();

    public static DataCache get(Context context) {
        String cachePath = getCachePath(context, config.cacheDir);
        File cacheDir = new File(cachePath + File.separator + config.cacheName);
        return get(cacheDir, config.maxSize);
    }

    public static DataCache get(Context context, String cacheName) {
        String cachePath = getCachePath(context, config.cacheDir);
        File cacheDir = new File(cachePath + File.separator + cacheName);
        return get(cacheDir, config.maxSize);
    }

    public static DataCache get(Context context, int cacheSize) {
        String cachePath = getCachePath(context, config.cacheDir);
        File cacheDir = new File(cachePath + File.separator + config.cacheName);
        return get(cacheDir, cacheSize);
    }

    public static DataCache get(Context context, String cacheName, int cacheSize) {
        String cachePath = getCachePath(context, config.cacheDir);
        File cacheDir = new File(cachePath + File.separator + cacheName);
        return get(cacheDir, cacheSize);
    }

    private static DataCache get(File cacheDir, int cacheSize) {
        DataCache dataCache = mInstanceMap.get(cacheDir.getAbsolutePath());
        if (dataCache == null) {
            dataCache = new DataCache(cacheDir, cacheSize);
            mInstanceMap.put(cacheDir.getAbsolutePath(), dataCache);
        }
        return dataCache;
    }

    private DataCache(File cacheDir, int cacheSize) {
        if (!cacheDir.exists() && !cacheDir.mkdirs()) {
            throw new RuntimeException("can't make dirs in " + cacheDir.getAbsolutePath());
        }
        memoryCache = new MemoryCache();
        diskCache = new DiskCache(cacheDir, config.appVersion, cacheSize);
    }

    private static String getCachePath(Context context, File cachePath) {
        String cachePathStr;
        if (cachePath != null) {
            if (!cachePath.exists() && !cachePath.mkdirs())
                cachePathStr = context.getCacheDir().getPath();
            else
                cachePathStr = cachePath.getPath();
        } else {
            cachePathStr = context.getCacheDir().getPath();
        }
        return cachePathStr;
    }

    public static Config config() {
        return config;
    }

    public static class Config {
        private File cacheDir;
        private String cacheName = CACHE_NAME;
        private int maxSize = MAX_SIZE;
        private int appVersion = APP_VERSION;

        public File getCacheDir() {
            return cacheDir;
        }

        public Config setCacheDir(File cacheDir) {
            if (!cacheDir.exists() && !cacheDir.mkdirs()) {
                throw new RuntimeException("can't make dirs in " + cacheDir.getAbsolutePath());
            }
            this.cacheDir = cacheDir;
            return this;
        }

        public String getCacheName() {
            return cacheName;
        }

        public Config setCacheName(String cacheName) {
            this.cacheName = cacheName;
            return this;
        }

        public int getMaxSize() {
            return maxSize;
        }

        public Config setMaxSize(int maxSize) {
            this.maxSize = maxSize;
            return this;
        }

        public int getAppVersion() {
            return appVersion;
        }

        /**
         * Note: when the version update is automatically cleaned up disk space
         * @param appVersion
         */
        public void setAppVersion(int appVersion) {
            this.appVersion = appVersion;
        }
    }

    public void putString(String key, String value) {
        memoryCache.put(key, value);
        diskCache.put(key, value);
    }

    public void putInt(String key, int value) {
        memoryCache.put(key, value);
        diskCache.put(key, Integer.valueOf(value));
    }

    public void putLong(String key, long value) {
        memoryCache.put(key, value);
        diskCache.put(key, Long.valueOf(value));
    }

    public void putDouble(String key, double value) {
        memoryCache.put(key, value);
        diskCache.put(key, Double.valueOf(value));
    }

    public void putFloat(String key, float value) {
        memoryCache.put(key, value);
        diskCache.put(key, Float.valueOf(value));
    }

    public void putBoolean(String key, boolean value) {
        memoryCache.put(key, value);
        diskCache.put(key, Boolean.valueOf(value));
    }

    public void putObject(String key, Object value) {
        memoryCache.put(key, value);
        diskCache.put(key, value);
    }

    public void putBytes(String key, byte[] bytes) {
        memoryCache.put(key, bytes);
        diskCache.put(key, bytes);
    }

    public String getString(String key) {
        String value = memoryCache.getString(key);
        if (value == null) {
            value = diskCache.getString(key);
            if (value != null)
                memoryCache.put(key, value);
        }
        return value;
    }

    public int getInt(String key, int defalut) {
        Integer value = memoryCache.getInt(key);
        if (value == null) {
            value = diskCache.getInt(key);
            if (value != null) {
                memoryCache.put(key, value);
                return value;
            } else {
                return defalut;
            }
        }
        return value;
    }

    public long getLong(String key, long defalut) {
        Long value = memoryCache.getLong(key);
        if (value == null) {
            value = diskCache.getLong(key);
            if (value != null) {
                memoryCache.put(key, value);
                return value;
            } else {
                return defalut;
            }
        }
        return value;
    }

    public double getDouble(String key, double defalut) {
        Double value = memoryCache.getDouble(key);
        if (value == null) {
            value = diskCache.getDouble(key);
            if (value != null) {
                memoryCache.put(key, value);
                return value;
            } else {
                return defalut;
            }
        }
        return value;
    }

    public float getFloat(String key, float defalut) {
        Float value = memoryCache.getFloat(key);
        if (value == null) {
            value = diskCache.getFloat(key);
            if (value != null) {
                memoryCache.put(key, value);
                return value;
            } else {
                return defalut;
            }
        }
        return value;
    }

    public boolean getBoolean(String key, boolean defalut) {
        Boolean value = memoryCache.getBoolean(key);
        if (value == null) {
            value = diskCache.getBoolean(key);
            if (value != null) {
                memoryCache.put(key, value);
                return value;
            } else {
                return defalut;
            }
        }
        return value;
    }

    @SuppressWarnings("unchecked")
    public Object getObject(String key) {
        Object object = memoryCache.getObject(key);
        if (object == null) {
            object = diskCache.getObject(key);
            if (object != null)
                memoryCache.put(key, object);
        }
        return object;
    }

    public byte[] getBytes(String key) {
        byte[] bytes = memoryCache.getBytes(key);
        if (bytes == null || bytes.length == 0) {
            bytes = diskCache.getBytes(key);
            if (bytes != null && bytes.length > 0)
                memoryCache.put(key, bytes);
        }
        return bytes;
    }

    public void clearData() {
        memoryCache.clear();
        diskCache.clear();
    }

    public void remove(String key) {
        diskCache.remove(key);
        memoryCache.remove(key);
    }

    public <T> void put(String key, T value) {
        if (key == null || "".equals(key) || value == null) {
            throw new NullPointerException("key or value can not be null.");
        }

        Type type = TypeToken.fromValue(value).getType();

        if (type == TypeToken.fromClass(String.class).getType()) {
            putString(key, String.valueOf(value));
        } else if (type == TypeToken.fromClass(Integer.class).getType()) {
            putInt(key, (Integer) value);
        } else if (type == TypeToken.fromClass(Long.class).getType()) {
            putLong(key, (Long) value);
        } else if (type == TypeToken.fromClass(Double.class).getType()) {
            putDouble(key, (Double) value);
        } else if (type == TypeToken.fromClass(Float.class).getType()) {
            putFloat(key, (Float) value);
        } else if (type == TypeToken.fromClass(Boolean.class).getType()) {
            putBoolean(key, (Boolean) value);
        } else if (type == TypeToken.fromClass(byte[].class).getType()){
            putBytes(key, (byte[]) value);
        } else {
            putObject(key, value);
        }
    }

    public <T> void putAsync(String key, T value) {
        putAsync(key, value, null);
    }

    public <T> void putAsync(final String key, final T value, final Callback callback) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                put(key, value);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (callback != null)
                    callback.finish();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void putStringAsync(String key, String value) {
        putStringAsync(key, value, null);
    }

    public void putStringAsync(final String key, final String value, final Callback callback) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                putString(key, value);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPostExecute(success);
                if (callback != null)
                    callback.finish();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void putIntAsync(String key, int value) {
        putIntAsync(key, value, null);
    }

    public void putIntAsync(final String key, final int value, final Callback callback) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                putInt(key, value);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                super.onPostExecute(success);
                if (callback != null)
                    callback.finish();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void putLongAsync(String key, long value) {
        putLongAsync(key, value, null);
    }

    public void putLongAsync(final String key, final long value, final Callback callback) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                putLong(key, value);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (callback != null)
                    callback.finish();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void putDoubleAsync(String key, double value) {
        putDoubleAsync(key, value, null);
    }

    public void putDoubleAsync(final String key, final double value, final Callback callback) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                putDouble(key, value);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (callback != null)
                    callback.finish();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void putFloatAsync(String key, float value) {
        putFloatAsync(key, value, null);
    }

    public void putFloatAsync(final String key, final float value, final Callback callback) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                putFloat(key, value);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (callback != null)
                    callback.finish();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void putBooleanAsync(String key, boolean value) {
        putBooleanAsync(key, value, null);
    }

    public void putBooleanAsync(final String key, final boolean value, final Callback callback) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                putBoolean(key, value);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (callback != null)
                    callback.finish();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void putBytesAsync(String key, byte[] value) {
        putBytesAsync(key, value, null);
    }

    public void putBytesAsync(final String key, final byte[] value, final Callback callback) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                putBytes(key, value);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (callback != null)
                    callback.finish();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public void putObjectAsync(String key, Object value) {
        putObjectAsync(key, value, null);
    }

    public void putObjectAsync(final String key, final Object value, final Callback callback) {
        new AsyncTask<Void, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... params) {
                putObject(key, value);
                return true;
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
                if (callback != null)
                    callback.finish();
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public interface Callback {
        void finish();
    }
}
