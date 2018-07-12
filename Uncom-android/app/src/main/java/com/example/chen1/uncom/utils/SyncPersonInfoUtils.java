package com.example.chen1.uncom.utils;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.widget.Toast;

import com.example.chen1.uncom.application.CoreApplication;
import com.example.chen1.uncom.bean.BeanDaoManager;
import com.example.chen1.uncom.bean.PersonDynamicsBean;
import com.example.chen1.uncom.bean.PersonDynamicsBeanDao;
import com.example.chen1.uncom.bean.RelationShipLevelBean;
import com.example.chen1.uncom.bean.UserBean;

import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by chen1 on 2018/2/20.
 */

public class SyncPersonInfoUtils {


    public void syncPersonInfo(final Object object){
        Flowable.create(new FlowableOnSubscribe<Object>() {
            @Override
            public void subscribe(FlowableEmitter<Object> e) throws Exception {
                PersonDynamicsBeanDao dao= BeanDaoManager.getInstance().getDaoSession().getPersonDynamicsBeanDao();
                QueryBuilder queryBuilder=dao.queryBuilder();
                if(object instanceof UserBean){
                    UserBean bean= (UserBean) object;
                    Query query=queryBuilder.where(PersonDynamicsBeanDao.Properties.User_id.eq(bean.getId())).build();
                    if(query!=null){
                        ArrayList<PersonDynamicsBean> beans= (ArrayList<PersonDynamicsBean>) query.list();
                        for (int i = 0; i <beans.size() ; i++) {
                            PersonDynamicsBean Personbean=beans.get(i);
                            Personbean.setUsername(bean.getUsername());
                            Personbean.setUser_photo(bean.getHeader_pic());
                            dao.update(Personbean);
                        }
                        e.onNext(beans);
                        e.onComplete();
                    }
                }else if(object instanceof RelationShipLevelBean){
                    RelationShipLevelBean bean= (RelationShipLevelBean) object;
                    Query query=queryBuilder.where(PersonDynamicsBeanDao.Properties.User_id.eq(bean.getMinor_user())).build();
                    if(query!=null){
                        ArrayList<PersonDynamicsBean> beans= (ArrayList<PersonDynamicsBean>) query.list();
                        for (int i = 0; i <beans.size() ; i++) {
                            PersonDynamicsBean Personbean=beans.get(i);
                            Personbean.setUsername(bean.getUsername());
                            Personbean.setUser_photo(bean.getHeader_pic());
                            dao.update(Personbean);
                        }
                        e.onNext(beans);
                        e.onComplete();
                    }

                }
            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Object>(){
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);
            }

            @SuppressLint("WrongConstant")
            @Override
            public void onNext(Object o) {
                Toast toast = Toast.makeText(CoreApplication.newInstance().getBaseContext(),
                        "同步成功", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.TOP, 0, 100);
                toast.setDuration(500);
                toast.show();
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
