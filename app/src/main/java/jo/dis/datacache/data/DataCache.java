package jo.dis.datacache.data;

import android.os.AsyncTask;

import java.lang.reflect.Type;

import jo.dis.datacache.data.cache.DiskCache;
import jo.dis.datacache.data.cache.MemoryCache;

/**
 * Created by Dis on 15/12/1.
 */
public class DataCache {

    private static MemoryCache memoryCache = null;
    private static DiskCache diskCache = null;

    /**
     *
     * @param path      缓存路径
     * @param cacheSize 缓存大小
     */
    public static void init(String path, int cacheSize) {
        memoryCache = new MemoryCache();
        diskCache = new DiskCache(path, 1, cacheSize);
    }

    private static void checkInit() {
        if (memoryCache == null || diskCache == null)
            throw new RuntimeException("DataCache not initialized.");
    }

    public static void putString(String key, String value) {
        checkInit();
        memoryCache.put(key, value);
        diskCache.put(key, value);
    }

    public static void putInt(String key, int value) {
        checkInit();
        memoryCache.put(key, value);
        diskCache.put(key, Integer.valueOf(value));
    }

    public static void putLong(String key, long value) {
        checkInit();
        memoryCache.put(key, value);
        diskCache.put(key, Long.valueOf(value));
    }

    public static void putDouble(String key, double value) {
        checkInit();
        memoryCache.put(key, value);
        diskCache.put(key, Double.valueOf(value));
    }

    public static void putFloat(String key, float value) {
        checkInit();
        memoryCache.put(key, value);
        diskCache.put(key, Float.valueOf(value));
    }

    public static void putBoolean(String key, boolean value) {
        checkInit();
        memoryCache.put(key, value);
        diskCache.put(key, Boolean.valueOf(value));
    }

    public static void putObject(String key, Object value) {
        checkInit();
        memoryCache.put(key, value);
        diskCache.put(key, value);
    }

    public static void putBytes(String key, byte[] bytes) {
        checkInit();
        memoryCache.put(key, bytes);
        diskCache.put(key, bytes);
    }

    public static String getString(String key) {
        checkInit();
        String value = memoryCache.getString(key);
        if (value == null) {
            value = diskCache.getString(key);
            if (value != null)
                memoryCache.put(key, value);
        }
        return value;
    }

    public static int getInt(String key, int defalut) {
        checkInit();
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

    public static long getLong(String key, long defalut) {
        checkInit();
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

    public static double getDouble(String key, double defalut) {
        checkInit();
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

    public static float getFloat(String key, float defalut) {
        checkInit();
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

    public static boolean getBoolean(String key, boolean defalut) {
        checkInit();
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
    public static Object getObject(String key) {
        checkInit();
        Object object = memoryCache.getObject(key);
        if (object == null) {
            object = diskCache.getObject(key);
            if (object != null)
                memoryCache.put(key, object);
        }
        return object;
    }

    public static byte[] getBytes(String key) {
        checkInit();
        byte[] bytes = memoryCache.getBytes(key);
        if (bytes == null || bytes.length == 0) {
            bytes = diskCache.getBytes(key);
            if (bytes != null && bytes.length > 0)
                memoryCache.put(key, bytes);
        }
        return bytes;
    }

    public static void clearData() {
        checkInit();
        memoryCache.clear();
        diskCache.clear();
    }

    public static void remove(String key) {
        checkInit();
        diskCache.remove(key);
        memoryCache.remove(key);
    }

    public static <T> void put(String key, T value) {
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

    public static <T> void putAsync(String key, T value) {
        putAsync(key, value, null);
    }

    public static <T> void putAsync(final String key, final T value, final Callback callback) {
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

    public static void putStringAsync(String key, String value) {
        putStringAsync(key, value, null);
    }

    public static void putStringAsync(final String key, final String value, final Callback callback) {
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

    public static void putIntAsync(String key, int value) {
        putIntAsync(key, value, null);
    }

    public static void putIntAsync(final String key, final int value, final Callback callback) {
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

    public static void putLongAsync(String key, long value) {
        putLongAsync(key, value, null);
    }

    public static void putLongAsync(final String key, final long value, final Callback callback) {
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

    public static void putDoubleAsync(String key, double value) {
        putDoubleAsync(key, value, null);
    }

    public static void putDoubleAsync(final String key, final double value, final Callback callback) {
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

    public static void putFloatAsync(String key, float value) {
        putFloatAsync(key, value, null);
    }

    public static void putFloatAsync(final String key, final float value, final Callback callback) {
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

    public static void putBooleanAsync(String key, boolean value) {
        putBooleanAsync(key, value, null);
    }

    public static void putBooleanAsync(final String key, final boolean value, final Callback callback) {
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

    public static void putBytesAsync(String key, byte[] value) {
        putBytesAsync(key, value, null);
    }

    public static void putBytesAsync(final String key, final byte[] value, final Callback callback) {
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

    public static void putObjectAsync(String key, Object value) {
        putObjectAsync(key, value, null);
    }

    public static void putObjectAsync(final String key, final Object value, final Callback callback) {
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
