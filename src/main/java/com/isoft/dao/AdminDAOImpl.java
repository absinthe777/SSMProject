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
        return sqlSession.selectList(sql);
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
    public int deleteUserById(String id) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        try {
            String sql = "com.isoft.mapping.Admin.deleteUserById";
            int delete = sqlSession.delete(sql, id);
            sqlSession.commit();
            return delete;
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        }
        return 0;
    }

    @Override
    public int activeAllUser() {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        String sql = "com.isoft.mapping.Admin.activeAllUser";
        return sqlSession.update(sql);
    }

    @Override
    public int activeUserById(String id) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        try {
            String sql = "com.isoft.mapping.Admin.activeUserById";
            int update = sqlSession.update(sql, id);
            sqlSession.commit();
            return update;
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        }
        return 0;
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
