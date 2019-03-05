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
        return sqlSession.selectList(sql, user_id);
    }

    @Override
    public Map findFilePathByDirId(String dir_id) {
        SqlSession sqlSession = sqlSessionFactoryBean.openSession(true);
        String sql="com.isoft.mapping.Dir.findFilePathByDirId";
        return sqlSession.selectOne(sql,dir_id);
    }
}
