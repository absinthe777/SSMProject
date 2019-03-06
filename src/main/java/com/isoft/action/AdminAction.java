package com.isoft.action;

import com.alibaba.fastjson.JSON;
import com.isoft.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@Scope("prototype")
@RequestMapping("/admin")
public class AdminAction {
    @Autowired
    IAdminService adminServiceImpl;


    @RequestMapping(value = "/findAllUser.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findAllUser(int page, int limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", (page - 1) * limit);
        map.put("limit", limit);
        List<Map<String, Object>> maps = adminServiceImpl.findAllUser(map);
        int count = Integer.parseInt(maps.get(maps.size() - 1).get("count").toString());
        maps.remove(maps.size() - 1);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("count", count);
        map1.put("data", maps);
        if (maps.size() > 0)
            map1.put("msg", "查询成功");
        else
            map1.put("msg", "还没有上传文件");
        map1.put("code", 0);
        return map1;
    }

    @RequestMapping(value = "/findUserInfo.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findUserInfo(int page, int limit) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", (page - 1) * limit);
        map.put("limit", limit);
        List<Map<String, Object>> maps = adminServiceImpl.findUserInfo(map);
        int count = Integer.parseInt(maps.get(maps.size() - 1).get("count").toString());
        maps.remove(maps.size() - 1);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("count", count);
        map1.put("data", maps);
        if (maps.size() > 0)
            map1.put("msg", "查询成功");
        else
            map1.put("msg", "还没有上传文件");
        map1.put("code", 0);
        return map1;
    }

    @RequestMapping(value = "/updateUserPwd.do", method = RequestMethod.GET)
    @ResponseBody
    public int updateFileName(String obj) {
        Map map = (Map) JSON.parse(obj);
        System.out.println(map + "---");
        int i = adminServiceImpl.updateUserPwd(map);
        return i;
    }

    @RequestMapping(value = "/updateUserManager.do", method = RequestMethod.GET)
    @ResponseBody
    public int updateUserManager(String obj) {
        Map map = (Map) JSON.parse(obj);
        System.out.println(map + "---");
        int i = adminServiceImpl.updateUserManager(map);
        return i;
    }

    @RequestMapping(value = "deleteUserById.do", method = RequestMethod.POST)
    @ResponseBody
    public int deleteUserById(String user_id) {
        String[] split = user_id.substring(1, user_id.length() - 1).split(",");
        List<String> strings = Arrays.asList(split);//把数组转化成List
        int i = adminServiceImpl.deleteUserById(strings);
        return i;
    }

    @RequestMapping(value = "/deleteOneUserById.do", method = RequestMethod.POST)
    @ResponseBody
    public int deleteOneFileById(String user_id) {
        List list = new ArrayList();
        list.add(user_id);
        int i = adminServiceImpl.deleteUserById(list);
        return i;
    }

    @RequestMapping(value = "/updateUserStatus.do", method = RequestMethod.POST)
    @ResponseBody
    public int updateUserStatus(String status, String user_id) {
        Map map = new HashMap();
        map.put("user_id", user_id);
        map.put("status", status);
        int i = adminServiceImpl.updateUserStatus(map);
        return i;
    }

    @RequestMapping(value = "insertOneUser.do", method = RequestMethod.POST)
    @ResponseBody
    public int insertOneUser(Map map) {
        int insert = adminServiceImpl.insertOneUser(map);
        return insert == 0? -1:insert;
    }

}
