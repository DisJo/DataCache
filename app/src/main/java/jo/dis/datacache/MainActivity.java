package jo.dis.datacache;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jo.dis.datacache.data.DataCache;

public class MainActivity extends AppCompatActivity {

    private final String STRING = "string";
    private final String INT = "int";
    private final String LONG = "long";
    private final String DOUBLE = "double";
    private final String FLOAT = "float";
    private final String BOOLEAN = "boolean";
    private final String BYTE = "byte";
    private final String OBJECT = "object";
    private final String LIST = "list";
    private final String BITMAP = "bitmap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView1 = (TextView) findViewById(R.id.tv);
        TextView textView2 = (TextView) findViewById(R.id.tv1);
        TextView textView3 = (TextView) findViewById(R.id.tv2);
        TextView textView4 = (TextView) findViewById(R.id.tv3);
        TextView textView5 = (TextView) findViewById(R.id.tv4);
        TextView textView6 = (TextView) findViewById(R.id.tv5);
        TextView textView7 = (TextView) findViewById(R.id.tv6);
        TextView textView8 = (TextView) findViewById(R.id.tv7);
        TextView textView9 = (TextView) findViewById(R.id.tv8);
        ImageView iv = (ImageView) findViewById(R.id.iv);

        // 设置字符串
        String str = "Hello world!";
        DataCache.putString(STRING, str);
        // 设置int
        DataCache.putInt(INT, 123);
        // 设置long
        DataCache.putLong(LONG, 20000l);
        // 设置double
        DataCache.putDouble(DOUBLE, 2.1d);
        // 设置flot
        DataCache.putFloat(FLOAT, 2.2f);
        // 设置boolean
        DataCache.putBoolean(BOOLEAN, true);
        // 设置byte[]
        DataCache.putBytes(BYTE, str.getBytes());
        // 设置类
        People people = new People(21, "Dis");
        DataCache.putObject(OBJECT, people);
        // 设置集合
        List<String> items = new ArrayList<>();
        items.add("item1");
        items.add("item2");
        items.add("item3");
        items.add("item4");
        items.add("item5");
        DataCache.putObject(LIST, items);
        // 设置bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        DataCache.putBitmap(BITMAP, bitmap);

        textView1.setText(DataCache.getString(STRING));
        textView2.setText(DataCache.getInt(INT, 0) + "");
        textView3.setText(DataCache.getLong(LONG, 0l) + "");
        textView4.setText(DataCache.getDouble(DOUBLE, 0.0d) + "");
        textView5.setText(DataCache.getFloat(FLOAT, 0.0f) + "");
        textView6.setText(DataCache.getBoolean(BOOLEAN, false) + "");
        textView7.setText(new String(DataCache.getBytes(BYTE)));
        People p = (People) DataCache.getObject(OBJECT);
        textView8.setText(p.getAge() + " " + p.getName());
        List<String> lists = (List<String>) DataCache.getObject(LIST);
        textView9.setText(lists.get(3));
        Bitmap bm = DataCache.getBitmap(BITMAP);
        iv.setImageBitmap(bm);

        // 泛型设置
        People pp = new People(23, "Jo");
        DataCache.put("T", pp);
        People ppp = (People) DataCache.getObject("T");
        Log.d("T=================>", "People age:" + ppp.getAge() + " " + "name:" + ppp.getName());

        // 异步设置
        DataCache.putStringAsync("Async", "asdfghjkl", new DataCache.Callback() {
            @Override
            public void finish() {
                String str = DataCache.getString("Async");
                Log.d("A=================>", str);
            }
        });
    }

}
