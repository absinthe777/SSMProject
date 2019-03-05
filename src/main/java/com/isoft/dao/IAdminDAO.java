package com.isoft.dao;

import java.util.List;
import java.util.Map;

public interface IAdminDAO {
    List<Map> findAllUser(); //Get all Users
    int deleteUserById(String id); // Delete a single user
    int activeAllUser(); // Active all users
    int activeUserById(String id); // Active a single user
    int insertOneUser(Map map); // Insert one user
}
