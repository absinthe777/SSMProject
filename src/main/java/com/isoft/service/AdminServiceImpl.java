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
    public int updateUserPwd(Map map) {
        return iAdminDAO.updateUserPwd(map);
    }

    @Override
    public int deleteUserById(String id) {
        return iAdminDAO.deleteUserById(id);
    }

    @Override
    public int activeAllUser() {
        return iAdminDAO.activeAllUser();
    }

    @Override
    public int activeUserById(String id) {
        return iAdminDAO.activeUserById(id);
    }

    @Override
    public int insertOneUser(Map map) {
        return iAdminDAO.insertOneUser(map);
    }
}
