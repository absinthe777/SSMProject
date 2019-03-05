package com.isoft.action;

import com.isoft.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@Scope("prototype")
@RequestMapping("/admin")
public class AdminAction {
    private final
    IAdminService AdminServiceImpl;

    @Autowired
    public AdminAction(IAdminService AdminServiceImpl) {
        this.AdminServiceImpl = AdminServiceImpl;
    }

    @RequestMapping(value = "/findAllUser.do", method = RequestMethod.POST)
    @ResponseBody
    public List<Map> findAllUser() {
        return AdminServiceImpl.findAllUser();
    } 
    
    @RequestMapping(value = "deleteUserById.do", method = RequestMethod.POST)
    @ResponseBody
    public int deleteUserById(String id) {
        int delete = AdminServiceImpl.deleteUserById(id);
        return delete == 0? -1:delete;
    }
    
    @RequestMapping(value = "activeAlluser.do", method = RequestMethod.POST)
    @ResponseBody
    public int activeAllUser() {
        int update = AdminServiceImpl.activeAllUser();
        return update == 0? -1:update;
    }

    @RequestMapping(value = "activeUserById.do", method = RequestMethod.POST)
    @ResponseBody
    public int activeUserById(String id) {
        int update = AdminServiceImpl.activeUserById(id);
        return update == 0? -1:update;
    }

    @RequestMapping(value = "insertOneUser.do", method = RequestMethod.POST)
    @ResponseBody
    public int insertOneUser(Map map) {
        int insert = AdminServiceImpl.insertOneUser(map);
        return insert == 0? -1:insert;
    }
}
