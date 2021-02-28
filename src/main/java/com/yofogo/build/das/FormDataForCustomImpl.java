package com.yofogo.build.das;

import cn.com.yofogo.frame.dao.perdure.BaseDao;
import cn.com.yofogo.frame.dao.perdure.conn.BaseDaoImpl;
import com.yofogo.build.entity.dto.FormElementField;
import com.yofogo.build.entity.dto.FormInfo;
import com.yofogo.build.entity.dto.query.AbsFormQueryDto;
import com.yofogo.build.entity.dto.query.FormCustomQueryDto;

import java.util.List;

public class FormDataForCustomImpl extends AbsFormDataImpl {

    @Override
    public FormInfo getFormInfo(AbsFormQueryDto formQueryDto) {
        FormCustomQueryDto dto = (FormCustomQueryDto) formQueryDto;
        FormInfo formInfo=get(dto.getFormId()+"");
        if(formInfo==null){
            BaseDao dbHelp= BaseDaoImpl.getInstance(dto.getDbSource());
            formInfo=dbHelp.queryObject(FormInfo.class, "SELECT * FROM form_info WHERE fiid=?", dto.getFormId());
            if(formInfo!=null) {
                List<FormElementField> fields=null;
                String sql="SELECT fe.fefid,fe.fiid,if(fe.names is null,fd.names,fe.names) AS names,IF(fe.tag IS NULL,fd.data_tag,fe.tag) AS tag,fe.types,fe.len,fe.default_val"
                        +",fe.is_must,fe.upload_type,fe.sorts,fd.fdfid,fd.db_type AS dbType,fd.data_tag AS dataTag,fd.select_items,fd.is_must AS fdfMust";
                if(formInfo.getTypes()==1){
                    sql += " FROM form_db_fields fd left join form_element_fields fe ON fe.fdfid=fd.fdfid WHERE fe.status=1"
                            +" AND fd.status=1 AND fd.fiid=? ORDER BY fe.sorts ,fd.sorts,fe.fefid";
                    fields=dbHelp.queryEntity(FormElementField.class, sql,dto.getFormId());
                } else {
                    sql += " FROM form_db_fields fd LEFT JOIN form_element_fields fe ON fe.fdfid=fd.fdfid AND fe.status=1 AND fe.fiid=?"
                            +" WHERE fd.status=1 AND fd.fiid=? ORDER BY fe.sorts,fd.sorts,fd.fdfid";
                    fields=dbHelp.queryEntity(FormElementField.class, sql,dto.getFormId(),dto.getFormId());
                }
                if(fields!=null && fields.size()>0) formInfo.setEleFields(fields);
                else return null;
                /*{
                    formInfo.setFormDataTables(dbHelp.queryEntity(FormDataTable.class,
                            "SELECT * FROM form_data_table WHERE fiid=? ORDER BY sorts,fdtid"
                            , dto.getFormId()));
                    formInfo.setFormDataWheres(dbHelp.queryEntity(FormDataWhere.class,
                            "SELECT * FROM form_data_where WHERE fiid=? ORDER BY sorts,fdwid"
                            , dto.getFormId()));
                }*/
            }
            if(formInfo!=null) add(dto.getFormId()+"", formInfo);
        }
        return formInfo;
    }
}
