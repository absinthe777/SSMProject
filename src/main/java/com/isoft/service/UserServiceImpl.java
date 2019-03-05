package com.isoft.service;

import com.isoft.dao.IUserDAO;
import com.isoft.pojo.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
public class UserServiceImpl implements IUserService {
    private final
    IUserDAO userDAO;

    @Autowired
    public UserServiceImpl(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Map<String,Object> login(String uname,String upwd){
        return userDAO.login(uname, upwd);
    }
    @Override
    public int register(Map<String, Object> obj) {
        return userDAO.register(obj);
    }

    @Override
    public int validateOldPwd(int userid, String oldpwd) {
        return userDAO.validateOldPwd(userid,oldpwd);
    }

    @Override
    public int updateOldPwd(int userid, String newpwd) {
        return userDAO.updateOldPwd(userid, newpwd);
    }

    @Override
    public int updateUserPhoto(String userid, String photoPath) {
        return userDAO.updateUserPhoto(userid, photoPath);
    }

    @Override
    public Map findUserInfoById(String user_id) {
        return userDAO.findUserInfoById(user_id);
    }

    @Override
    public int updateUserInfo(UserInfo userinfo) {
        return userDAO.updateUserInfo(userinfo);
    }

    @Override
    public List<Map> PieAnalysis() {
        return userDAO.PieAnalysis();
    }

    @Override
    public List<Map> dirAnalysis(String user_id) {
        return userDAO.dirAnalysis(user_id);
    }

    @Override
    public List<Map> LineAnalysis() {
        return userDAO.LineAnalysis();
    }

    @Override
    public List<Map> BarAnalysis() {
        return userDAO.BarAnalysis();
    }

    @Override
    public List<Map> RadarAnalysis() {
        return userDAO.RadarAnalysis();
    }
}
