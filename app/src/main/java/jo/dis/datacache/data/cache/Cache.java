package jo.dis.datacache.data.cache;

import android.graphics.Bitmap;

/**
 * Created by Dis on 15/12/1.
 */
public interface Cache {

    String getString(String key);

    Integer getInt(String key);

    Long getLong(String key);

    Object getDouble(String key);

    Float getFloat(String key);

    Boolean getBoolean(String key);

    Object getObject(String key);

    byte[] getBytes(String key);

    Bitmap getBitmap(String key);


    void put(String key, Object value);

    void remove(String key);

    void clear();

}
