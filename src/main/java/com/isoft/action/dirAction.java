package com.isoft.action;

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
}
