package com.yofogo.build.das;

import com.yofogo.build.entity.dto.FormInfo;
import com.yofogo.build.entity.dto.query.AbsFormQueryDto;

public interface IFormData {
    public FormInfo getFormInfo(AbsFormQueryDto formQueryDto);
}
