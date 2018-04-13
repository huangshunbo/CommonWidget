package com.android.basiclib.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class Student {
    @Id(autoincrement = true)
    private long id;
    @NotNull
    private String name;
    @NotNull
    private int age;
    @Generated(hash = 2046055486)
    public Student(long id, @NotNull String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }
    @Generated(hash = 1556870573)
    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
