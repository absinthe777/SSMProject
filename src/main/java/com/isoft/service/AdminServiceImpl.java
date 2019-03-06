package com.isoft.service;

import com.isoft.dao.IAdminDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("IAdminService")
public class AdminServiceImpl implements IAdminService {
    private final
    IAdminDAO iAdminDAO;

    @Autowired
    public AdminServiceImpl(IAdminDAO iAdminDAO) {
        this.iAdminDAO = iAdminDAO;
    }


    @Override
    public List<Map<String, Object>> findAllUser(Map map) {
        return iAdminDAO.findAllUser(map);
    }

    @Override
    public List<Map<String, Object>> findUserInfo(Map map) {
        return iAdminDAO.findUserInfo(map);
    }

    @Override
    public int updateUserPwd(Map map) {
        return iAdminDAO.updateUserPwd(map);
    }

    @Override
    public int updateUserManager(Map map) {
        return iAdminDAO.updateUserManager(map);
    }

    @Override
    public int deleteUserById(List<String> list) {
        return iAdminDAO.deleteUserById(list);
    }

    @Override
    public int updateUserStatus(Map map) {
        return iAdminDAO.updateUserStatus(map);
    }

    @Override
    public int insertOneUser(Map map) {
        return iAdminDAO.insertOneUser(map);
    }
}
