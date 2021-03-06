package com.isoft.dao;

import java.util.List;
import java.util.Map;

public interface IDirDAO {
    List<Map> dirAnalysis(String user_id);
    Map findFilePathByDirId(String dir_id);
    List<Map> findAllDirByUserId(String user_id);
    int deleteDirByDirId(List<String> strings);
    int addDir(Map<String, Object> obj);
}
