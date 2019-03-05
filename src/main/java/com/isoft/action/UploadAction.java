package com.isoft.action;

import com.isoft.service.IDirService;
import com.isoft.service.IFileService;
import com.isoft.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Controller
@Scope("prototype")
@RequestMapping("/file")
public class UploadAction {
    private final
    IUserService userServiceImpl;
    private final
    IFileService fileServiceImpl;
    private final
    IDirService iDirService;

    @Autowired
    public UploadAction(IUserService userServiceImpl, IFileService fileServiceImpl, IDirService iDirService) {
        this.userServiceImpl = userServiceImpl;
        this.fileServiceImpl = fileServiceImpl;
        this.iDirService = iDirService;
    }

    @RequestMapping(value = "/uploadSelectFile.do", method = RequestMethod.POST)
    @ResponseBody
    public Map uploadSelectFile(HttpSession session,@RequestParam("file") MultipartFile file, String file_type, String user_id, String file_dir_id) {
        String realPath = session.getServletContext().getRealPath("/WEB-INF/resourses");
        Map filePath = iDirService.findFilePathByDirId(file_dir_id);
        File fileDir=new File(realPath+"/"+filePath.get("filepath"));
        if(!fileDir.exists()){
            fileDir.mkdirs();//创建文件夹
        }
        String fileName = file.getOriginalFilename();//获得上传文件的文件名称
        File newFile = null;
        if (fileName != null) {
            newFile = new File(realPath+filePath.get("filepath"), fileName);
        }
        Map<String, Object> map = new HashMap<>();
        try {
            file.transferTo(newFile);//实现文件上传的方法
            //完成数据表信息添加
            Map<String, Object> fileInfomap = new HashMap<>();
            fileInfomap.put("file_name",file.getOriginalFilename());
            fileInfomap.put("file_size",file.getSize());
            fileInfomap.put("file_type",file_type);
            fileInfomap.put("file_dir_id",file_dir_id);
            fileInfomap.put("user_id",user_id);
            int i = fileServiceImpl.insertUploadFileInfo(fileInfomap);
            map.put("code", 0);
            if(i>0)
                map.put("dt","1");
            else
                map.put("dt",0);//dt表示插入数据表状态
        } catch (Exception e) {
            map.put("code", 1);
            e.printStackTrace();
        }
        return map;
    }
    @RequestMapping(value = "/downloadFile.do",method = RequestMethod.GET)
    @ResponseBody
    public int downloadFile_1(int file_id, String filePath, String fileName, HttpServletRequest request, HttpServletResponse response) {
        System.out.println(filePath + "/" + fileName);
        String realPath = request.getSession().getServletContext().getRealPath("WEB-INF/resourses") +
                filePath + "/" + fileName;
        System.out.println(realPath);
        try {
            File file = new File(realPath);
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-disposition", "attachment; filename=" +
                    new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(file.length()));
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
            byte[] size = new byte[1024];
            int length = 0;
            while ((length = bis.read(size)) != -1) {
                bos.write(size, 0, length);
            }
            bos.flush();
            bos.close();
            bis.close();
            int i = fileServiceImpl.updateDownloadCount(file_id);
            System.out.println("更新成功");
            return i;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @RequestMapping(value = "/uploadPersonPhoto.do",method = RequestMethod.POST)
    @ResponseBody
    public Map uploadPersonPhoto(@RequestParam("file") MultipartFile file, HttpSession session) {
        String userid = session.getAttribute("userid").toString();
        Map<String, Object> map = new HashMap<>();
        System.out.println("---" + userid);
        try {
            if (file != null) {
                String fileName = file.getOriginalFilename();
                String extName = fileName.substring(fileName.lastIndexOf("."));
                List list = new ArrayList();
                list.add(".jpg");
                list.add(".png");
                list.add(".gif");
                list.add(".bmp");
                list.add(".jpeg");
                if (list.contains(extName.toLowerCase())) {
                    String realPath = session.getServletContext().getRealPath("myphoto");
                    String photoFileName = new Date().getTime() + extName;//使用时间戳进行文件命名
                    String descPath = realPath + "/" + photoFileName;
                    System.out.println(descPath);
                    file.transferTo(new File(realPath, photoFileName));
                    //图片路径和文件名称保存到数据库中
                    //根据用户ID更新头像
                    int i = userServiceImpl.updateUserPhoto(userid, "myphoto/"  + photoFileName);
                    System.out.println("upload"+i);
                    if (i > 0) {
                        System.out.println("上传头像成功");
                        map.put("photo","myphoto/" + photoFileName);
                        map.put("code",0);//上传头像成功，数据库存储路径成功
                    } else
                        map.put("code",3);//数据库存储路径不成功
                } else {
                    map.put("code",2);//文件类型错误
                }
            } else {
                map.put("code",4);//上传文件为空
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code",1);// 上传异常
        }
        return map;
    }
}