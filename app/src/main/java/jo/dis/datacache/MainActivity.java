package jo.dis.datacache;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import jo.dis.library.data.DataCache;

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
        TextView textView10 = (TextView) findViewById(R.id.tv9);
        final TextView textView11 = (TextView) findViewById(R.id.tv10);

        final DataCache dataCache = DataCache.get(this);
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
        // 设置flot
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

        textView1.setText(dataCache.getString(STRING));
        textView2.setText(otherCache.getInt(INT, 0) + "");
        textView3.setText(dataCache.getLong(LONG, 0l) + "");
        textView4.setText(dataCache.getDouble(DOUBLE, 0.0d) + "");
        textView5.setText(dataCache.getFloat(FLOAT, 0.0f) + "");
        textView6.setText(dataCache.getBoolean(BOOLEAN, false) + "");
        byte[] newByte = dataCache.getBytes(BYTE);
        textView7.setText(new String(newByte));
        People p = (People) dataCache.getObject(OBJECT);
        textView8.setText(p.getAge() + " " + p.getName());
        List<String> lists = (List<String>) dataCache.getObject(LIST);
        textView9.setText(lists.get(3));

        // 泛型设置
        People pp = new People(23, "Jo");
        dataCache.put("T", pp);
        People ppp = (People) dataCache.getObject("T");
        textView10.setText("People age:" + ppp.getAge() + " " + "name:" + ppp.getName());

        // 异步设置
        dataCache.putStringAsync("Async", "asdfghjkl", new DataCache.Callback() {
            @Override
            public void finish() {
                String str = dataCache.getString("Async");
                textView11.setText(str);
            }
        });
    }

}
