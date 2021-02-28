package com.yofogo.build;

import com.alibaba.fastjson.JSONObject;
import com.yofogo.build.das.FormDataForDBImpl;
import com.yofogo.build.das.IFormData;
import com.yofogo.build.entity.dto.FormInfo;
import com.yofogo.build.entity.dto.query.AbsFormQueryDto;
import com.yofogo.build.entity.dto.query.FormDBQueryDto;
import com.yofogo.build.entity.enums.BuildConmand;
import com.yofogo.build.entity.po.ProInfo;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Test {
    public static void main(String[] args){
        tempbuildInfo();
        System.out.println("OK");

    }


    private static void tempbuildInfo(){

        Set<BuildConmand> conmands = new HashSet();
        conmands.add(BuildConmand.ENTITY);
        conmands.add(BuildConmand.DAO);
        conmands.add(BuildConmand.BIZ);
        //String conmands="entity,service,controller";//,project,pageView";
        //String conmands="project";

        String module="system",//模块名，模块包子包名   item、user、activity、thirdparty
                dbSource="ssweixin",//对应dbconfig中的name  ssweixin、yofoys_activities
                dbName="ssweixin";//数据库名    ssweixin、yofoys_activities
        Map<String,String> tableForDesc=new HashMap<String,String>();

        tableForDesc.put("config_ss_operate","折扣配置外层信息表");
        tableForDesc.put("config_ss_operate_item","具体折扣信息列表");
        tableForDesc.put("config_ss_operate_threshold","折扣门槛信息表");



        ProInfo project=new ProInfo();
        project.setOutPath("C:\\Users\\Administrator\\Desktop");
        project.setDatabaseName(dbSource);
        project.setOutName("test-build-user");
        project.setBasePackage("com.yofoys.services."+module);
        for(String tableName : tableForDesc.keySet()){
            //ssweixin yofoys_activities

            IFormData formData = new FormDataForDBImpl();
            FormDBQueryDto formQueryDto = new FormDBQueryDto();
            formQueryDto.setDbSource(dbSource);
            formQueryDto.setDbName(dbName);
            formQueryDto.setTableName(tableName);
            formQueryDto.setTableDesc(tableForDesc.get(tableName));

            FormInfo form=formData.getFormInfo(formQueryDto);//.findFormInfo("ssweixin","ssweixin",tableName,tableForDesc);
            form.setModuleName(module);
            com.yzz.cms.build.BuildHandler.buildConmand(conmands, project, form);
        }

    }

}
