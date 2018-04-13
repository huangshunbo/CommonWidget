package com.android.basiclib.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Unique;

@Entity
public class Local {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String key;
    @NotNull
    private String value;
    @Generated(hash = 121876467)
    public Local(Long id, @NotNull String key, @NotNull String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    public Local(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Generated(hash = 1337064102)
    public Local() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
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

    public void setId(Long id) {
        this.id = id;
    }
}
