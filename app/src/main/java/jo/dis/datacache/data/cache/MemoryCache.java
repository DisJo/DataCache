package jo.dis.datacache.data.cache;

import android.graphics.Bitmap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Dis on 15/12/1.
 */
public class MemoryCache implements Cache {

    private ConcurrentMap<String, Object> hashMap;

    public MemoryCache() {
        this.hashMap = new ConcurrentHashMap<> ();
    }

    @Override
    public String getString(String key) {
        if (hashMap.containsKey(key))
            return String.valueOf(hashMap.get(key));
        return null;
    }

    @Override
    public Integer getInt(String key) {
        if (hashMap.containsKey(key))
            return (Integer) hashMap.get(key);
        return null;
    }

    @Override
    public Long getLong(String key) {
        if (hashMap.containsKey(key))
            return (Long) hashMap.get(key);
        return null;
    }

    @Override
    public Double getDouble(String key) {
        if (hashMap.containsKey(key))
            return (Double) hashMap.get(key);
        return null;
    }

    @Override
    public Float getFloat(String key) {
        if (hashMap.containsKey(key))
            return (Float) hashMap.get(key);
        return null;
    }

    @Override
    public Boolean getBoolean(String key) {
        if (hashMap.containsKey(key))
            return (Boolean) hashMap.get(key);
        return null;
    }

    @Override
    public Object getObject(String key) {
        if (hashMap.containsKey(key))
            return hashMap.get(key);
        return null;
    }

    @Override
    public byte[] getBytes(String key) {
        if (hashMap.containsKey(key))
            return (byte[]) hashMap.get(key);
        return new byte[0];
    }

    @Override
    public Bitmap getBitmap(String key) {
        if (hashMap.containsKey(key))
            return (Bitmap) hashMap.get(key);
        return null;
    }

    @Override
    public void put(String key, Object value) {
        if (value instanceof Bitmap || value instanceof String) {
            hashMap.put(key, value);
        } else if (value instanceof Integer) {
            hashMap.put(key, new Integer((int) value));
        } else if (value instanceof Long) {
            hashMap.put(key, new Long((long) value));
        } else if (value instanceof Double) {
            hashMap.put(key, new Double((double) value));
        } else if (value instanceof Float) {
            hashMap.put(key, new Float((float) value));
        } else if (value instanceof Boolean) {
            hashMap.put(key, new Boolean((boolean) value));
        } else {
            hashMap.put(key, value);
        }
    }

    @Override
    public void remove(String key) {
        if (hashMap.containsKey(key))
            hashMap.remove(key);
    }

    @Override
    public void clear() {
        hashMap.clear();
    }
}
