package com.android.basiclib.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class LocalStorage {
    @Id(autoincrement = true)
    private long id;
    @NotNull
    private String key;
    @NotNull
    private String value;

    @Generated(hash = 494552724)
    public LocalStorage() {
    }
    @Generated(hash = 324982708)
    public LocalStorage(long id, @NotNull String key, @NotNull String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }
    public String getKey() {
        return this.key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }

}
