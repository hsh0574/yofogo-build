package com.yofogo.build.das;

import cn.com.yofogo.frame.dao.DBModes;
import cn.com.yofogo.frame.dao.perdure.BaseDao;
import cn.com.yofogo.frame.dao.perdure.conn.BaseDaoImpl;
import com.yofogo.build.entity.dto.FormElementField;
import com.yofogo.build.entity.dto.FormInfo;
import com.yofogo.build.entity.dto.query.AbsFormQueryDto;
import com.yofogo.build.entity.dto.query.FormDBQueryDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FormDataForDBImpl extends AbsFormDataImpl {

    @Override
    public FormInfo getFormInfo(AbsFormQueryDto formQueryDto) {
        FormDBQueryDto dto = (FormDBQueryDto) formQueryDto;
        FormInfo formInfo=get(dto.getTableName());
        if(formInfo==null){
            formInfo=new FormInfo();
            formInfo.setCellCount(2);//表单显示列数
            formInfo.setTypes(2);//独立表
            formInfo.setNames(dto.getTableDesc());
            formInfo.setTag(dto.getTableName());
            formInfo.setTableName(dto.getTableName());
            formInfo.setPath(dto.getTableName());
            String sql="select COLUMN_NAME,EXTRA from information_schema.columns where COLUMN_KEY='PRI' AND table_name=?";
            BaseDao dbHelp= BaseDaoImpl.getInstance(dto.getDbSource());
            Map tableInfo=dbHelp.queryMap(sql, DBModes.ResultMode.isOnlyMap, dto.getTableName());
            if("auto_increment".equalsIgnoreCase(tableInfo.get("EXTRA")+"")) formInfo.setPrimaryType(1);
            else formInfo.setPrimaryType(2);//默认为程序生成
            formInfo.setPrimaryKey(tableInfo.get("COLUMN_NAME")+"");

            sql="select COLUMN_NAME as dataTag,COLUMN_NAME as tag,IF(IS_NULLABLE='NO',1,0) AS isMust,DATA_TYPE AS dbType"
                    +",IF(CHARACTER_MAXIMUM_LENGTH IS NULL,NUMERIC_PRECISION,CHARACTER_MAXIMUM_LENGTH) AS len"
                    +",COLUMN_COMMENT AS names"
                    +" from information_schema.columns where TABLE_SCHEMA=? AND table_name=?";
            List<FormElementField> fields=dbHelp.queryEntity(FormElementField.class, sql,dto.getDbName(), dto.getTableName());
            if(fields!=null && fields.size()>0) formInfo.setEleFields(fields);
			/*else  return null;

			//写死
			{

				formInfo.setFormDataTables(dbHelp.queryEntity(FormDataTable.class,
						"SELECT dt.* FROM form_data_table dt,form_info fi WHERE dt.fiid=fi.fiid AND fi.table_name=? ORDER BY dt.sorts"
						, tableName));

				formInfo.setFormDataWheres(dbHelp.queryEntity(FormDataWhere.class,
						"SELECT dw.* FROM form_data_where dw,form_info fi WHERE dw.fiid=fi.fiid AND fi.table_name=? ORDER BY dw.sorts"
						, tableName));
			}*/
            if(formInfo.getEleFields().size()>0) add(dto.getTableName(), formInfo);
        }
        return formInfo;
    }
}
