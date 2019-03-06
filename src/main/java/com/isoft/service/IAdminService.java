package com.isoft.service;

import java.util.List;
import java.util.Map;

public interface IAdminService {
    List<Map<String, Object>> findAllUser(Map map); //Get all Users
    List<Map<String, Object>> findUserInfo(Map map); //Get all Users
    int updateUserPwd(Map map);
    int updateUserManager(Map map);
    int deleteUserById(List<String> list); // Delete a single user
    int updateUserStatus(Map map); // Active users
    int insertOneUser(Map map); // Insert one user
}

