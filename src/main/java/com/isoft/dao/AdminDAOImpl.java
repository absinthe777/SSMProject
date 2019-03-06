package com.isoft.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository("adminDAO")
public class AdminDAOImpl implements IAdminDAO{
    @Autowired
    SqlSessionFactory sqlSessionFactoryBean;


    @Override
    public List<Map<String, Object>> findAllUser(Map map) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        String sql = "com.isoft.mapping.Admin.findAllUser";
        String sql_count = "com.isoft.mapping.Admin.findRSCount";
        try{
            List<Map<String, Object>> objects = sqlSession.selectList(sql, map);
            Map<String, Object> rscount = sqlSession.selectOne(sql_count, map);
            objects.add(rscount);
            return objects;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> findUserInfo(Map map) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        String sql = "com.isoft.mapping.Admin.findUserInfo";
        String sql_count = "com.isoft.mapping.Admin.findRSCount";
        try{
            List<Map<String, Object>> objects = sqlSession.selectList(sql, map);
            Map<String, Object> rscount = sqlSession.selectOne(sql_count, map);
            objects.add(rscount);
            return objects;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int updateUserPwd(Map map) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        String sql = "com.isoft.mapping.Admin.updateUserPwd";
        int i = sqlSession.update(sql, map);
        sqlSession.commit(true);
        return i;
    }

    @Override
    public int updateUserManager(Map map) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        String sql = "com.isoft.mapping.Admin.updateUserManager";
        int i = sqlSession.update(sql, map);
        sqlSession.commit(true);
        return i;
    }

    @Override
    public int deleteUserById(List<String> list) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        String sql = "com.isoft.mapping.Admin.deleteUserById";
        try {
            int dele = sqlSession.delete(sql, list);
            sqlSession.commit(true);
            return dele;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int updateUserStatus(Map map) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        String sql = "com.isoft.mapping.Admin.updateUserStatus";
        int update = sqlSession.update(sql, map);
        return update;
    }

    @Override
    public int insertOneUser(Map map) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        try {
            String sql = "com.isoft.mapping.Admin.insertOneUser";
            int insert = sqlSession.insert(sql, map);
            sqlSession.commit();
            return insert;
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        }
        return 0;
    }
}
