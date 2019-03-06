package com.isoft.service;

import com.isoft.dao.IDirDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("IDirService")
public class DirServiceImpl implements IDirService {
    private final
    IDirDAO iDirDAO;

    @Autowired
    public DirServiceImpl(IDirDAO iDirDAO) {
        this.iDirDAO = iDirDAO;
    }

    @Override
    public List<Map> dirAnalysis(String user_id) {
        List<Map> list = iDirDAO.dirAnalysis(user_id);
        return list;
    }

    @Override
    public Map findFilePathByDirId(String dir_id) {
        Map filePathByDirId = iDirDAO.findFilePathByDirId(dir_id);
        return filePathByDirId;
    }

    @Override
    public List<Map> findAllDirByUserId(String user_id) {
        List<Map> allDirList = iDirDAO.findAllDirByUserId(user_id);
        return allDirList;
    }

    @Override
    public int deleteDirByDirId(List<String> strings) {
        return iDirDAO.deleteDirByDirId(strings);
    }

    @Override
    public int addDir(Map<String, Object> obj) {
        return iDirDAO.addDir(obj);
    }
}
