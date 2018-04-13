package com.android.basiclib.data;

import com.android.basiclib.bean.Local;
import com.android.basiclib.data.gen.LocalDao;
import com.android.basiclib.data.manager.DaoManager;
import com.android.basiclib.log.LogUtil;

import java.util.List;

public class DLocal {
    private static LocalDao localDao = DaoManager.getInstance().getDaoSession().getLocalDao();

    private DLocal(){}

    public static void save(String key,int value){
        save(key,""+value);
    }
    public static void save(String key,float value){
        save(key,""+value);
    }
    public static void save(String key,long value){
        save(key,""+value);
    }
    public static void save(String key,boolean value){
        save(key,value ? 1 : 0);
    }
    public static void save(String key,String value){
        List<Local> locals = localDao.queryBuilder().where(LocalDao.Properties.Key.eq(key)).list();
        if(locals.size() > 0){
            Local local = locals.get(0);
            local.setValue(value);
            localDao.update(local);
        }else {
            localDao.insert(new Local(key,value));
        }
    }
    public static String getString(String key,String def){
        List<Local> locals = localDao.queryBuilder().where(LocalDao.Properties.Key.eq(key)).list();
        if(locals.size() > 0){
            return locals.get(0).getValue();
        }else {
            return def;
        }
    }
    public static long getLong(String key,long def){
        String tmp = getString(key,""+def);

        return Long.valueOf(tmp);
    }
    public static int getInt(String key,int def){
        String tmp = getString(key,""+def);
        return Integer.parseInt(tmp);
    }
    public static float getFloat(String key,float def){
        String tmp = getString(key,""+def);
        return Float.valueOf(tmp);
    }
    public static boolean getBoolean(String key,boolean def){
        String tmp = getString(key,def ? "1" : "0");
        return tmp.equals("1") ? true : false;
    }
    public static void printAll(){
        LogUtil.d(localDao.loadAll());
    }
}
