package cn.didadu.sample.jndi;

import java.io.Serializable;
import java.rmi.Remote;

/**
 * Created by zhangjing on 16-10-11.
 */
public class Person implements Remote, Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString() {
        return "name:" + name + " password:" + password;
    }

}
