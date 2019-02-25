package com.isoft.action;

import com.alibaba.fastjson.JSON;
import com.isoft.pojo.UserInfo;
import com.isoft.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@Scope("prototype")
@RequestMapping("/user")
public class UserAction {
    @Autowired
    IUserService userServiceImpl;

    @RequestMapping(value = "/findUserInfoById.do",method = RequestMethod.GET)
    @ResponseBody
    public Map findUserInfoById(@RequestParam("userid") String user_id){
        Map userInfo = userServiceImpl.findUserInfoById(user_id);
        return userInfo;
    }

    @RequestMapping(value = "/updateOldUpwd.do",method = RequestMethod.POST)
    @ResponseBody
    public int updateOldUpwd(int userid, String oldpwd, String newpwd){
        int validate = userServiceImpl.validateOldPwd(userid, oldpwd);
        if(validate==0){
            return -1;
        }
        else {
            int i=userServiceImpl.updateOldPwd(userid,newpwd);
            return i;
        }
    }

    @RequestMapping(value = "/updateInfo.do",method = RequestMethod.POST)
    @ResponseBody
    public int updateUserInfo(UserInfo userinfo){
        int i = userServiceImpl.updateUserInfo(userinfo);
        return i;
    }

    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    @ResponseBody
    public Map login(String uname, String upwd, HttpSession session){
        Map<String, Object> login = userServiceImpl.login(uname, upwd);
        HashMap map=new HashMap();
        if(login==null){
            map.put("loginmsg",0);
            return map;
        }else{
            map.put("loginmsg",1);
            map.put("userid",login.get("user_id").toString());
            session.setAttribute("userid",login.get("user_id").toString());
            map.put("status",login.get("status").toString());
            session.setAttribute("status",login.get("status").toString());
            if(login.get("photo")==null||login.get("photo").toString()=="")
                map.put("photo","myphoto/myphoto.jpg");
            else
                map.put("photo",login.get("photo").toString());
           /* System.out.println(JSON.toJSONString(map));*/
            return map;
        }
    }

    @RequestMapping(value = "/register.do",method = RequestMethod.POST)
    @ResponseBody
    public int register(String uname, String upwd, String email){
        Map map=new HashMap();
        map.put("uname",uname);
        map.put("upwd",upwd);
        map.put("email",email);
        int register = userServiceImpl.register(map);
        if(register==0)
            return 0;
        else
            return 1;
    }

}
