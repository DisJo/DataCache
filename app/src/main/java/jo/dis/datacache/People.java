package jo.dis.datacache;/**
 * Created by yjx on 15/5/23.
 */

import java.io.Serializable;

/**
 * Created by Dis on 15/12/1.
 */
public class People implements Serializable {
    private int age;
    private String name;

    public People(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
