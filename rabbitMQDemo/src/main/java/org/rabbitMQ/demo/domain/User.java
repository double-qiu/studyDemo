package org.rabbitMQ.demo.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by qy on 2016/7/23.
 */
public class User implements Serializable {
    
    private String name;
    private int age;
    private Date birthday;
    
    public User() {
        
    }
    
    public User(String name, int age, Date birthday) {
        this.name = name;
        this.age = age;
        this.birthday = birthday;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
