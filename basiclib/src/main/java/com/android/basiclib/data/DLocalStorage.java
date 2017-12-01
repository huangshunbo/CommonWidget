package com.android.basiclib.data;

import com.android.basiclib.bean.LocalStorage;
import com.android.basiclib.data.gen.LocalStorageDao;

public class DLocalStorage {

    private static final LocalStorageDao localStorageDao = DaoManager.getInstance().getDaoSession().getLocalStorageDao();

    public static void add(String key,String value){
        LocalStorage localStorage = new LocalStorage();
        localStorage.setKey(key);
        localStorage.setValue(value);
        localStorageDao.insert(localStorage);
    }
    public static String load(String key){
//        return localStorageDao.load(key).getValue();
        return "";
    }
    public static String loadAll(){
        return localStorageDao.loadAll().toString();
    }


}
