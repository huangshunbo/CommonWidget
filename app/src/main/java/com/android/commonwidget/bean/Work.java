package com.android.commonwidget.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

public class Work {
    private long id;
    private String projectName;
    private int level;
    public Work(long id, @NotNull String projectName, int level) {
        this.id = id;
        this.projectName = projectName;
        this.level = level;
    }
    public Work() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getProjectName() {
        return this.projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public int getLevel() {
        return this.level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

}
