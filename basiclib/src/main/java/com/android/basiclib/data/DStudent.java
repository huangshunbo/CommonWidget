package com.android.basiclib.data;

import com.android.basiclib.bean.Student;
import com.android.basiclib.data.gen.StudentDao;
import com.android.basiclib.data.manager.DaoManager;

import java.util.List;

public class DStudent {

    private StudentDao studentDao = DaoManager.getInstance().getDaoSession().getStudentDao();
    private DStudent(){}
    public long insert(Student student){
        return studentDao.insert(student);
    }
    public void save(Student student){
        studentDao.save(student);
    }
    public void delete(Student student){
        studentDao.delete(student);
    }
    public void delete(long key){
        studentDao.deleteByKey(key);
    }
    public void deleteAll(){
        studentDao.deleteAll();
    }
    public void update(Student student){
        studentDao.update(student);
    }
    public Student load(long key){
       return studentDao.load(key);
    }
    public Student loadByRowId(long rowId){
        return studentDao.loadByRowId(rowId);
    }
    public List<Student> loadAll(){
        return studentDao.loadAll();
    }
}
