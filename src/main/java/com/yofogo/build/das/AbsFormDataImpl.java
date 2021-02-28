package com.yofogo.build.das;

import com.yofogo.build.entity.dto.FormInfo;
import com.yofogo.build.entity.dto.query.AbsFormQueryDto;

import java.util.HashMap;
import java.util.Map;

public abstract class AbsFormDataImpl implements IFormData {

    private final static Map<String,FormInfo> formInfos=new HashMap<String,FormInfo>();

    protected static FormInfo get(String formIdOrName){
        return formInfos.get(formIdOrName);
    }
    protected static void add(String formIdOrName,FormInfo formInfo){
        formInfos.put(formIdOrName, formInfo);
    }
    protected static FormInfo remove(String formIdOrName){
        return formInfos.remove(formIdOrName);
    }
}
