package com.isoft.action;

import com.alibaba.fastjson.JSON;
import com.isoft.service.IDirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/dir")
@Scope("prototype")
public class dirAction {
    @Autowired
    IDirService iDirService;

    @RequestMapping("/dirAnalysis.do")
    @ResponseBody
    public Map dirAnalysis(String user_id){
        List<Map> maps = iDirService.dirAnalysis(user_id);
        List newList = new ArrayList();
        Map set = new HashMap();
        for (Map list : maps) {
            set.put(list.get("dir_name"),list.get("parent_dir"));
        }
        Iterator iterator = set.keySet().iterator();
        while (iterator.hasNext()) {
            Map map=new HashMap();
            String name=iterator.next().toString();
            map.put("name",name);
            map.put("value",set.get(name));
            List l=new ArrayList();
            for (Map m: maps) {
                if(map.containsValue(m.get("dir_name"))){
                    Map mm=new HashMap();
                    mm.put("name",m.get("name"));
                    mm.put("value",m.get("dir_id"));
                    l.add(mm);
                }}

            map.put("children",l);
            newList.add(map);
        }
        Map map1=new HashMap();
        map1.put("name","目录结构");
        map1.put("children",newList);
        return map1;
    }

    @RequestMapping("/findAllDirByUserId.do")
    @ResponseBody
    public Map findAllDirByUserId(String user_id) {
        List<Map> allDirByUserId = iDirService.findAllDirByUserId(user_id);
        System.out.println(JSON.toJSONString(allDirByUserId));
        Map map = new HashMap();
        map.put("msg", "success");
        map.put("code", 0);
        map.put("data", allDirByUserId);
        map.put("count", allDirByUserId.size());
        map.put("is", true);
        map.put("tip", "查询成功");
        return map;
    }

    @RequestMapping(value = "/addDir.do")
    @ResponseBody
    public int addDir(String dir_name, String parent_dir, String dir_user_id) {
        Map map = new HashMap();
        map.put("dir_name",dir_name);
        map.put("dir_user_id",dir_user_id);
        map.put("parent_dir",parent_dir);
        Map filepath = iDirService.findFilePathByDirId(parent_dir);
        String dir = filepath.get("filepath").toString() + "/";
        map.put("dir_path", dir + dir_name + "/");
        return iDirService.addDir(map);
    }

    @RequestMapping(value = "/deleteDirByDirId.do")
    @ResponseBody
    public int deleteFileById(String dir_id) {
        String[] split = dir_id.substring(0, dir_id.length()-1).split(",");
        List<String> strings = Arrays.asList(split);//把数组转化成List
        return iDirService.deleteDirByDirId(strings);
    }
}
