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
}
