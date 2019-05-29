package db;

import com.example.sqlliteopenhelperdemo2.dao.DaoSession;

import java.util.List;

import bean.Demo;

public class DBUtlis {
    public static List<Demo> queryArr(){
        DaoSession daoSession = MyApplication.getDaoSession();
        return daoSession.loadAll(Demo.class);
    }

    public static void insert (Demo demo){
        DaoSession daoSession = MyApplication.getDaoSession();
        daoSession.insert(demo);
    }
}
