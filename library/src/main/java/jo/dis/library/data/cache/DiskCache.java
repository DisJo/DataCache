package jo.dis.library.data.cache;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jo.dis.library.data.DiskLruCache;

/**
 * Created by Dis on 15/12/1.
 */
public class DiskCache implements Cache {

    private DiskLruCache diskLruCache;
    private File cacheDir;

    /**
     * @param cacheDir
     * @param version
     * @param cacheSize
     */
    public DiskCache(File cacheDir, int version, int cacheSize) {
        this.cacheDir = cacheDir;
        try {
            diskLruCache = DiskLruCache.open(cacheDir, version, 1, cacheSize);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getString(String key) {
        byte[] bytes = getBytes(key);
        if (bytes != null)
            return new String(bytes);
        return null;
    }

    @Override
    public Integer getInt(String key) {
        Object obj = getObject(key);
        if (obj != null)
            return (Integer) obj;
        return null;
    }

    @Override
    public Long getLong(String key) {
        Object obj = getObject(key);
        if (obj != null)
            return (Long) obj;
        return null;
    }

    @Override
    public Double getDouble(String key) {
        Object obj = getObject(key);
        if (obj != null)
            return (Double) obj;
        return null;
    }

    @Override
    public Float getFloat(String key) {
        Object obj = getObject(key);
        if (obj != null)
            return (Float) obj;
        return null;
    }

    @Override
    public Boolean getBoolean(String key) {
        Object obj = getObject(key);
        if (obj != null)
            return (Boolean) obj;
        return null;
    }

    @Override
    public Object getObject(String key) {
        try {
            key = hashKeyForDisk(key);
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
            if (snapshot != null && snapshot.getInputStream(0) != null) {
                ObjectInputStream ois = new ObjectInputStream(snapshot.getInputStream(0));
                return ois.readObject();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public byte[] getBytes(String key) {
        try {
            key = hashKeyForDisk(key);
            DiskLruCache.Snapshot snapshot = diskLruCache.get(key);
            if (snapshot != null && snapshot.getInputStream(0) != null)
                return write(snapshot.getInputStream(0));
            else
                return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void put(String key, Object value) {
        try {
            key = hashKeyForDisk(key);
            DiskLruCache.Editor editor = diskLruCache.edit(key);
            OutputStream os = editor.newOutputStream(0);
            if (value instanceof byte[]) {
                os.write((byte[]) value);
                os.close();
            } else if (value instanceof String) {
                os.write(((String) value).getBytes());
                os.close();
            } else {
                ObjectOutputStream objOs = new ObjectOutputStream(os);
                objOs.writeObject(value);
                os.close();
                objOs.close();
            }
            editor.commit();
            diskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(String key) {
        try {
            diskLruCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clear() {
        File file = this.cacheDir;
        if (file.exists()) {
            File[] files = file.listFiles();
            for (File f : files) {
                f.delete();
            }
        }
    }

    private byte[] write(InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int len = 0;
        byte[] bytes = new byte[1024];
        try {
            while ((len = in.read(bytes, 0, bytes.length)) != -1) {
                out.write(bytes, 0, len);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    private String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
}
