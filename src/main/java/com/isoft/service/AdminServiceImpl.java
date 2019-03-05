package com.isoft.service;

import com.isoft.dao.IAdminDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("AdminService")
public class AdminServiceImpl implements IAdminService {
    private final
    IAdminDAO iAdminDAO;

    @Autowired
    public AdminServiceImpl(IAdminDAO iAdminDAO) {
        this.iAdminDAO = iAdminDAO;
    }

    @Override
    public List<Map> findAllUser() {
        return iAdminDAO.findAllUser();
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
