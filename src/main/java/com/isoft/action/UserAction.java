package com.isoft.action;

import com.isoft.pojo.UserInfo;
import com.isoft.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

@Controller
@Scope("prototype")
@RequestMapping("/user")
public class UserAction {
    @Value("${smtp}")
    String smtp;
    @Value("${from}")
    String from;
    @Value("${authCode}")
    String authCode;
    private final
    IUserService userServiceImpl;

    @Autowired
    public UserAction(IUserService userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @RequestMapping(value = "/PieAnalysis.do",method = RequestMethod.GET)
    @ResponseBody
    public Map PieAnalysis(){
        List<Map> list  = userServiceImpl.PieAnalysis();
        List<String> statusList=new ArrayList<String>();
        List<Integer> countList=new ArrayList<Integer>();
        for (Map map:list){
            statusList.add(map.get("status").toString());
            countList.add(Integer.parseInt(map.get("count").toString()));
        }
        Map<String, Object> map=new HashMap<>();
        map.put("status",statusList);
        map.put("count",countList);
        return map;
    }

    @RequestMapping("/dirAnalysis.do")
    @ResponseBody
    public Map dirAnalysis(String user_id) {
        List<Map> maps = userServiceImpl.dirAnalysis(user_id);
        List newList = new ArrayList();
        Set set = new HashSet();
        for (Map list : maps) {
            set.add(list.get("dir_name"));
        }
        for (Object o : set) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", o);
            List l = new ArrayList();
            for (Map m : maps) {
                if (map.containsValue(m.get("dir_name"))) {
                    Map<String, Object> mm = new HashMap<>();
                    mm.put("name", m.get("name"));
                    mm.put("value", 0);
                    l.add(mm);
                }
            }

            map.put("children", l);
            newList.add(map);
        }
        Map<String, Object> map1=new HashMap<>();
        map1.put("name","目录结构");
        map1.put("children",newList);
        return map1;
    }

    @RequestMapping(value = "/LineAnalysis.do",method = RequestMethod.GET)
    @ResponseBody
    public Map LineAnalysis(){
        List<Map> list  = userServiceImpl.LineAnalysis();
        List<String> nameList=new ArrayList<String>();
        List<Integer> downloadCountList=new ArrayList<Integer>();
        for (Map map:list){
            nameList.add(map.get("file_name").toString());
            downloadCountList.add(Integer.parseInt(map.get("downloadCount").toString()));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("file_name",nameList);
        map.put("downloadCount",downloadCountList);
        return map;
    }

    @RequestMapping(value = "/BarAnalysis.do",method = RequestMethod.GET)
    @ResponseBody
    public Map BarAnalysis(){
        List<Map> list  = userServiceImpl.BarAnalysis();
        List<String> nameList=new ArrayList<String>();
        List<Integer> sizeList=new ArrayList<Integer>();
        for (Map map:list){
            nameList.add(map.get("file_name").toString());
            sizeList.add(Integer.parseInt(map.get("file_size").toString()));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("file_name",nameList);
        map.put("file_size",sizeList);
        return map;
    }

    @RequestMapping(value = "/RadarAnalysis.do",method = RequestMethod.GET)
    @ResponseBody
    public Map RadarAnalysis(){
        List<Map> list  = userServiceImpl.RadarAnalysis();
        List<String> typeList=new ArrayList<String>();
        for (Map map:list){
            typeList.add(map.get("file_type").toString());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("file_type",typeList);
        return map;
    }

    @RequestMapping(value = "/findUserInfoById.do",method = RequestMethod.GET)
    @ResponseBody
    public Map findUserInfoById(@RequestParam("userid") String user_id){
        return userServiceImpl.findUserInfoById(user_id);
    }

    @RequestMapping(value = "/updateOldUpwd.do",method = RequestMethod.POST)
    @ResponseBody
    public int updateOldUpwd(int userid, String oldpwd, String newpwd){
        int validate = userServiceImpl.validateOldPwd(userid, oldpwd);
        if(validate==0){
            return -1;
        }
        else
            return userServiceImpl.updateOldPwd(userid,newpwd);
    }

    @RequestMapping(value = "/updateInfo.do",method = RequestMethod.POST)
    @ResponseBody
    public int updateUserInfo(UserInfo userinfo){
        return userServiceImpl.updateUserInfo(userinfo);
    }

    @RequestMapping(value = "/login.do",method = RequestMethod.POST)
    @ResponseBody
    public Map login(String uname, String upwd, HttpSession session){
        Map<String, Object> login = userServiceImpl.login(uname, upwd);
        Map<String, Object> map = new HashMap<>();
        if(login==null){
            map.put("loginmsg",0);
            return map;
        }else{
            map.put("loginmsg",1);
            map.put("userid",login.get("user_id").toString());
            session.setAttribute("userid",login.get("user_id").toString());
            map.put("status",login.get("status").toString());
            session.setAttribute("status",login.get("status").toString());
            if(login.get("photo")==null||login.get("photo").toString().equals(""))
                map.put("photo","myphoto/myphoto.jpg");
            else
                map.put("photo",login.get("photo").toString());
           /* System.out.println(JSON.toJSONString(map));*/
            return map;
        }
    }

    @RequestMapping(value = "/findpwd.do",method = RequestMethod.POST)
    @ResponseBody
    public Map findpwd(String uname, String email, HttpSession session){
        Map<String, Object> findpwd = userServiceImpl.findpwd(uname, email);
        Map<String, Object> map = new HashMap<>();
        if(findpwd==null){
            map.put("vermsg",0);
            return map;
        }else{
            map.put("vermsg",1);
            map.put("userid",findpwd.get("user_id").toString());
            session.setAttribute("userid",findpwd.get("user_id").toString());
            return map;
        }
    }

    @RequestMapping(value = "/findUserPwd.do", method = RequestMethod.POST)
    @ResponseBody
    public  String findUserPwd(HttpServletRequest request,String userId, String email) throws MessagingException {
        JavaMailSenderImpl javaMailSender=new JavaMailSenderImpl();
        javaMailSender.setHost(smtp);
        javaMailSender.setUsername(from);
        javaMailSender.setPassword(authCode);   //授权码
        MimeMessage mimeMessage=javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper=new MimeMessageHelper(mimeMessage,true,"utf-8");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setFrom(from);
        mimeMessage.setSubject("找回密码邮件");
        String hrefString=request.getScheme()+"://"+request.getServerName()+":"+request.getLocalPort()
                +"/"+request.getServletContext().getContextPath()+"/user/findUserPwd2.do?user_id="+userId;
        mimeMessage.setText("单击下面链接修改密码："+hrefString);
        javaMailSender.send(mimeMessage);
        return "邮件已发送，请到邮箱查看。";
    }

    @RequestMapping(value = "/findUserPwd2.do")
    @ResponseBody
    public void findUserPwd3(HttpServletRequest request, HttpServletResponse response, String userId) throws ServletException, IOException {
        request.getRequestDispatcher("../findUserPwd3.html").forward(request, response);
    }

    @RequestMapping(value = "/findUserPwd3.do")
    @ResponseBody
    public ModelAndView findUserPwd2(int userId, String newPwd) {
        Map<String, Object> map = new HashMap<>();
        userServiceImpl.updateOldPwd(userId, newPwd);
        map.put("vermsg", 1);
        return new ModelAndView("redirect:../findUserPwd4.html");
    }

        @RequestMapping(value = "/findUserSuccess.do")
        @ResponseBody
        public void findUserSuccess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.getRequestDispatcher("../findUserPwd4.html").forward(request, response);
    }

    @RequestMapping(value = "/register.do",method = RequestMethod.POST)
    @ResponseBody
    public int register(String uname, String upwd, String email){
        Map<String, Object> map = new HashMap<>();
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
