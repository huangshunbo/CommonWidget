package com.android.basiclib.data.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.android.basiclib.bean.Local;
import com.android.basiclib.bean.Student;

import com.android.basiclib.data.gen.LocalDao;
import com.android.basiclib.data.gen.StudentDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig localDaoConfig;
    private final DaoConfig studentDaoConfig;

    private final LocalDao localDao;
    private final StudentDao studentDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        localDaoConfig = daoConfigMap.get(LocalDao.class).clone();
        localDaoConfig.initIdentityScope(type);

        studentDaoConfig = daoConfigMap.get(StudentDao.class).clone();
        studentDaoConfig.initIdentityScope(type);

        localDao = new LocalDao(localDaoConfig, this);
        studentDao = new StudentDao(studentDaoConfig, this);

        registerDao(Local.class, localDao);
        registerDao(Student.class, studentDao);
    }
    
    public void clear() {
        localDaoConfig.clearIdentityScope();
        studentDaoConfig.clearIdentityScope();
    }

    public LocalDao getLocalDao() {
        return localDao;
    }

    public StudentDao getStudentDao() {
        return studentDao;
    }

}
