package com.isoft.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository("iDirDAO")
public class DirDAOImpl implements IDirDAO{
    @Autowired
    SqlSessionFactory sqlSessionFactoryBean;

    @Override
    public List<Map> dirAnalysis(String user_id) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        String sql="com.isoft.mapping.Dir.dirAnalysis";
        List<Map> objects = sqlSession.selectList(sql, user_id);
        return objects;
    }

    @Override
    public Map findFilePathByDirId(String dir_id) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        String sql="com.isoft.mapping.Dir.findFilePathByDirId";
        Map objects = sqlSession.selectOne(sql,dir_id);
        return objects;
    }

    @Override
    public List<Map> findAllDirByUserId(String user_id) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        String sql="com.isoft.mapping.Dir.findAllDir";
        List<Map> objects = sqlSession.selectList(sql, user_id);
        return objects;
    }

    @Override
    public int deleteDirByDirId(List<String> strings) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        String sql="com.isoft.mapping.Dir.deleteDirByDirId";
        int delete = sqlSession.delete(sql, strings);
        sqlSession.commit(true);
        return delete;
    }

    @Override
    public int addDir(Map<String, Object> obj) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        String sql="com.isoft.mapping.Dir.addDir";
        return sqlSession.insert(sql, obj);
    }
}
