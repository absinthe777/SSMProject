package com.isoft.action;

import com.alibaba.fastjson.JSON;
import com.isoft.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
@RequestMapping("/admin")
public class AdminAction {
    @Autowired
    IAdminService adminServiceImpl;


    @RequestMapping(value = "/findAllUser.do", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findAllUser() {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> maps = adminServiceImpl.findAllUser(map);
        maps.remove(maps.size() - 1);
        Map<String, Object> map1 = new HashMap<>();
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

    @RequestMapping(value = "deleteUserById.do", method = RequestMethod.POST)
    @ResponseBody
    public int deleteUserById(String id) {
        int delete = adminServiceImpl.deleteUserById(id);
        return delete == 0? -1:delete;
    }
    
    @RequestMapping(value = "activeAlluser.do", method = RequestMethod.POST)
    @ResponseBody
    public int activeAllUser() {
        int update = adminServiceImpl.activeAllUser();
        return update == 0? -1:update;
    }

    @RequestMapping(value = "activeUserById.do", method = RequestMethod.POST)
    @ResponseBody
    public int activeUserById(String id) {
        int update = adminServiceImpl.activeUserById(id);
        return update == 0? -1:update;
    }

    @RequestMapping(value = "insertOneUser.do", method = RequestMethod.POST)
    @ResponseBody
    public int insertOneUser(Map map) {
        int insert = adminServiceImpl.insertOneUser(map);
        return insert == 0? -1:insert;
    }
}
