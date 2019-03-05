package com.isoft.dao;

import com.isoft.pojo.UserInfo;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Repository("userDAO")
public class UserDAOImpl implements IUserDAO {
    @Autowired
    SqlSessionFactory sqlSessionFactoryBean;
    @Override
    public Map<String, Object> login(String uname, String upwd) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        String sql="com.isoft.mapping.User.login";
        Map map = new HashMap();
        map.put("uname",uname);
        map.put("upwd",upwd);
        Map<String, Object> o = sqlSession.selectOne(sql,map);
        return o;
    }

    @Override
    public int register(Map<String, Object> obj) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        String sql="com.isoft.mapping.User.register";
        int insert = sqlSession.insert(sql, obj);
        return insert;
    }

    @Override
    public int validateOldPwd(int userid, String oldpwd) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        String sql="com.isoft.mapping.User.validateOldPwd";
        Map map = new HashMap();
        map.put("userid",userid);
        map.put("oldpwd",oldpwd);
        Map<String, Object> o = sqlSession.selectOne(sql,map);
        if(o == null)
            return 0;
        else
            return 1;
    }

    @Override
    public int updateOldPwd(int userid, String newpwd) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        String sql="com.isoft.mapping.User.updateOldPwd";
        Map map = new HashMap();
        map.put("userid",userid);
        map.put("newpwd",newpwd);
        int update = sqlSession.update(sql,map);
        return update;
    }

    @Override
    public int updateUserPhoto(String userid, String photoPath) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        String sql="com.isoft.mapping.User.updateUserPhoto";
        Map map = new HashMap();
        map.put("userid",userid);
        map.put("photopath",photoPath);
        int update = sqlSession.update(sql,map);
        sqlSession.commit();
        return update;
    }

    @Override
    public Map findUserInfoById(String user_id) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        String sql="com.isoft.mapping.User.findUserInfoById";
        Map<String,Object> o = sqlSession.selectOne(sql, user_id);
        return o;
    }

    @Override
    public int updateUserInfo(UserInfo userinfo) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        try {
            String sql = "com.isoft.mapping.User.updateUserInfo";
            int update = sqlSession.update(sql, userinfo);
            sqlSession.commit();
            return update;
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
        }
        return 0;
    }

    @Override
    public List<Map> PieAnalysis() {
        try {
            SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
            String sql = "com.isoft.mapping.User.userStatusAnalysis";
            List<Map> objects =sqlSession.selectList(sql);
            return objects;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map> dirAnalysis(String user_id) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        String sql="com.isoft.mapping.User.dirAnalysis";
        List<Map> objects = sqlSession.selectList(sql, user_id);
        return objects;
    }

    @Override
    public List<Map> LineAnalysis() {
        try {
            SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
            String sql = "com.isoft.mapping.User.LineAnalysis";
            List<Map> objects =sqlSession.selectList(sql);
            return objects;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map> BarAnalysis() {
        try {
            SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
            String sql = "com.isoft.mapping.User.BarAnalysis";
            List<Map> objects =sqlSession.selectList(sql);
            return objects;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map> RadarAnalysis() {
        try {
            SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
            String sql = "com.isoft.mapping.User.RadarAnalysis";
            List<Map> objects =sqlSession.selectList(sql);
            return objects;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
