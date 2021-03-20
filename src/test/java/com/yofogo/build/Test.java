package com.yofogo.build;

import com.yofogo.build.das.FormDataForDBImpl;
import com.yofogo.build.das.IFormData;
import com.yofogo.build.entity.dto.FormInfo;
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

        String module="goods",//模块名，模块包子包名   item、user、activity、thirdparty
                dbSource="ssweixin",//对应dbconfig中的name  ssweixin、yofoys_activities
                dbName="ssweixin";//数据库名    ssweixin、yofoys_activities
        Map<String,String> tableForDesc=new HashMap<String,String>();

        tableForDesc.put("gd_plate_goods","板块商品信息");
        tableForDesc.put("gd_plate_info","板块信息");
        tableForDesc.put("gd_plate_module","板块模块信息");
        tableForDesc.put("gd_plate_cats","板块分类信息");
        tableForDesc.put("gd_plate_brand","板块品牌信息");
        tableForDesc.put("item_brand","商品品牌信息");



        ProInfo project=new ProInfo();
        project.setOutPath("C:\\Users\\Administrator\\Desktop");
        project.setDatabaseName(dbSource);
        project.setOutName("test-build-user");
        project.setBasePackage("com.yofoys.services");
        project.setChildPackage("."+module);
        project.setModuleServiceName("YOFOYS-ITEM-SERVICES");
        project.setDelTablePrefix("gd_");
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
            BuildHandler.buildConmand(conmands, project, form);
        }

    }

}
