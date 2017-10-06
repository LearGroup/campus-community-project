package com.example.chen1.uncom.bean;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.chen1.uncom.bean.UserBean;
import com.example.chen1.uncom.bean.RelationShipLevelBean;

import com.example.chen1.uncom.bean.UserBeanDao;
import com.example.chen1.uncom.bean.RelationShipLevelBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userBeanDaoConfig;
    private final DaoConfig relationShipLevelBeanDaoConfig;

    private final UserBeanDao userBeanDao;
    private final RelationShipLevelBeanDao relationShipLevelBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userBeanDaoConfig = daoConfigMap.get(UserBeanDao.class).clone();
        userBeanDaoConfig.initIdentityScope(type);

        relationShipLevelBeanDaoConfig = daoConfigMap.get(RelationShipLevelBeanDao.class).clone();
        relationShipLevelBeanDaoConfig.initIdentityScope(type);

        userBeanDao = new UserBeanDao(userBeanDaoConfig, this);
        relationShipLevelBeanDao = new RelationShipLevelBeanDao(relationShipLevelBeanDaoConfig, this);

        registerDao(UserBean.class, userBeanDao);
        registerDao(RelationShipLevelBean.class, relationShipLevelBeanDao);
    }
    
    public void clear() {
        userBeanDaoConfig.clearIdentityScope();
        relationShipLevelBeanDaoConfig.clearIdentityScope();
    }

    public UserBeanDao getUserBeanDao() {
        return userBeanDao;
    }

    public RelationShipLevelBeanDao getRelationShipLevelBeanDao() {
        return relationShipLevelBeanDao;
    }

}
