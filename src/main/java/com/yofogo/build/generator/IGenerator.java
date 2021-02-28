package com.yofogo.build.generator;

import com.yofogo.build.entity.dto.FormInfo;
import com.yofogo.build.entity.po.ProInfo;

import java.util.List;

public interface IGenerator {

    boolean buildEntity(ProInfo project, FormInfo formInfo);

    boolean buildDas(ProInfo project, FormInfo formInfo, List<String> methods);

    boolean buildBizAndImpl(ProInfo project,FormInfo formInfo);

}
