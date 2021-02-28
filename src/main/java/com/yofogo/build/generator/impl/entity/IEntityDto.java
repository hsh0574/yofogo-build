package com.yofogo.build.generator.impl.entity;

import com.yofogo.build.entity.dto.FormInfo;
import com.yofogo.build.entity.po.ProInfo;

public interface IEntityDto {

    boolean buildEntityPo(ProInfo project, FormInfo formInfo);

    boolean buildEntityDto(ProInfo project,FormInfo formInfo);
}
