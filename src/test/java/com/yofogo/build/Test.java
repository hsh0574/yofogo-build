package com.yofogo.build;

import cn.com.yofogo.tools.util.MyMath;
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
        //tempbuildInfo();
        String path="https://wd.yofogo.com/itemdetail?igid=%s";
        if(path.indexOf("%s")>0) {
            path = String.format(path, "IG001");
        }
        System.out.println(path);
/*

        String phone="13812345678";
        System.out.println(phone.replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
*/

    }


    private static void tempbuildInfo(){

        Set<BuildConmand> conmands = new HashSet();
        conmands.add(BuildConmand.ENTITY);
        conmands.add(BuildConmand.DAO);
        conmands.add(BuildConmand.BIZ);
        //String conmands="entity,service,controller";//,project,pageView";
        //String conmands="project";


        Map<String,String> tableForDesc=new HashMap<String,String>();

        tableForDesc.put("sale_unite_fn_note","团促销售信息");
        tableForDesc.put("sale_unite_info","团促信息");


       /* tableForDesc.put("order_child","子订单商品信息");
        tableForDesc.put("orders","主订单信息");
        tableForDesc.put("order_recommend","订单销售推荐信息");
        tableForDesc.put("refund","售后单信息");
        tableForDesc.put("refund_items","售后单商品");*/



        ProInfo project=new ProInfo();
        ModleInfo modleInfo;

        modleInfo = trade(project);
        //modleInfo = incomeCenter(project);
        //modleInfo = activity(project);
        //modleInfo = activityOld(project);//ssweixin
        //modleInfo = component(project);
        //modleInfo = income(project);
        //modleInfo = user(project);
        //modleInfo = goods(project);
        //modleInfo = currency(project);
        //modleInfo = pay(project);
        //modleInfo = analyze(project);
        //modleInfo = areaProxy(project);
        //modleInfo = log(project);


        /**
         * 以下不用修改
         */
        project.setOutPath("C:\\Users\\Administrator\\Desktop");
        project.setBasePackage("com.yofoys.services");
        //project.setBasePackage("com.yofogo.assist");
        project.setChildPackage("."+modleInfo.getModule());
        String dbSource="ssweixin";
        for(String tableName : tableForDesc.keySet()){
            //ssweixin yofoys_activities

            IFormData formData = new FormDataForDBImpl();
            FormDBQueryDto formQueryDto = new FormDBQueryDto();
            formQueryDto.setDbSource(dbSource);
            formQueryDto.setDbName(modleInfo.getDbName());
            formQueryDto.setTableName(tableName);
            formQueryDto.setTableDesc(tableForDesc.get(tableName));

            FormInfo form=formData.getFormInfo(formQueryDto);//.findFormInfo("ssweixin","ssweixin",tableName,tableForDesc);
            form.setModuleName(modleInfo.getModule());
            BuildHandler.buildConmand(conmands, project, form);
        }
        System.out.println(project.getJavaBasePath());
    }


    private static ModleInfo activity(ProInfo project){
        project.setDatabaseName("activity");
        project.setModuleServiceName("${yofoys.modules.service-activity}");
        project.setDelTablePrefix("pk_");

        project.setOutName("test-build");
        //表所在数据库
        return new ModleInfo("yofoys_activities","activity");
    }

    private static ModleInfo activityOld(ProInfo project){
        project.setDatabaseName("ssweixin");
        project.setModuleServiceName("${yofoys.modules.service-activity}");
        project.setDelTablePrefix("pk_");

        project.setOutName("test-build");
        //表所在数据库
        return new ModleInfo("ssweixin","activity");
    }

    private static ModleInfo trade(ProInfo project){
        //对应dbconfig中的name：ssweixin
        project.setDatabaseName("ssweixin");
        project.setModuleServiceName("${yofoys.modules.center-trade}");
        project.setDelTablePrefix("");

        project.setOutName("test-build");
        //表所在数据库
        return new ModleInfo("ssweixin","trade");
    }

    private static ModleInfo income(ProInfo project){
        //对应dbconfig中的name：ssweixin
        project.setDatabaseName("ssweixin");
        project.setModuleServiceName("${yofoys.modules.service-income}");
        project.setDelTablePrefix("");

        project.setOutName("test-build");
        //表所在数据库
        return new ModleInfo("ssweixin","income");
    }


    private static ModleInfo incomeCenter(ProInfo project){
        //对应dbconfig中的name：ssweixin
        project.setDatabaseName("ssweixin");
        //${yofoys.modules.center-incomec}
        project.setModuleServiceName("${yofoys.modules.center-income}");
        project.setDelTablePrefix("");

        project.setOutName("test-build");
        //表所在数据库
        return new ModleInfo("ssweixin","income");
    }

    private static ModleInfo incomejyzs(ProInfo project){
        //对应dbconfig中的name：yofoysIncome
        project.setDatabaseName("yofoysIncome");
        //${yofoys.modules.center-incomec}
        project.setModuleServiceName("${yofoys.modules.center-incomejyzs}");
        project.setDelTablePrefix("shop_");

        project.setOutName("test-build");
        //表所在数据库
        return new ModleInfo("yofoys_income","incomec");
    }

    private static ModleInfo user(ProInfo project){
        //对应dbconfig中的name：ssweixin、yofoysAreaProxy、yofoysContent、yofoys_activities、currency
        project.setDatabaseName("ssweixin");
        project.setModuleServiceName("${yofoys.modules.center-user}");
        project.setDelTablePrefix("");

        project.setOutName("test-build");
        //表所在数据库
        return new ModleInfo("ssweixin","user");
    }

    private static ModleInfo goods(ProInfo project){
        //对应dbconfig中的name：ssweixin、yofoysAreaProxy、yofoysContent、yofoys_activities、currency
        project.setDatabaseName("ssweixin");
        //${yofoys.modules.center-areaproxy}
        project.setModuleServiceName("${yofoys.modules.center-goods}");
        project.setDelTablePrefix("");

        project.setOutName("test-build");
        //表所在数据库
        return new ModleInfo("ssweixin","goods");
    }

    private static ModleInfo pay(ProInfo project){
        //对应dbconfig中的name：ssweixin、yofoysAreaProxy、yofoysContent、yofoys_activities、currency
        project.setDatabaseName("ssweixin");
        //${yofoys.modules.center-areaproxy}
        project.setModuleServiceName("${yofoys.modules.center-pay}");
        project.setDelTablePrefix("");

        project.setOutName("test-build");
        //表所在数据库
        return new ModleInfo("ssweixin","pay");
    }

    private static ModleInfo component(ProInfo project){
        //对应dbconfig中的name：ssweixin、yofoysAreaProxy、yofoysContent、yofoys_activities、currency
        project.setDatabaseName("yofoys_component");
        project.setModuleServiceName("${yofoys.modules.center-component}");
        project.setDelTablePrefix("");
        project.setOutName("test-build");
        //表所在数据库
        return new ModleInfo("yofoys_component","component");
    }

    private static ModleInfo analyze(ProInfo project){
        //对应dbconfig中的name：ssweixin、yofoysAreaProxy、yofoysContent、yofoys_activities、currency
        project.setDatabaseName("yofoys_stat");
        project.setModuleServiceName("${yofoys.modules.service-analyze}");
        project.setDelTablePrefix("");
        project.setOutName("test-build");
        //表所在数据库
        return new ModleInfo("yofoys_stat","analyze");
    }

    private static ModleInfo currency(ProInfo project){
        //对应dbconfig中的name：ssweixin、yofoysAreaProxy、yofoysContent、yofoys_activities、currency
        project.setDatabaseName("currency");
        //${yofoys.modules.center-areaproxy}
        project.setModuleServiceName("${yofoys.modules.service-currency}");
        project.setDelTablePrefix("consume_");

        project.setOutName("test-build");
        //表所在数据库
        return new ModleInfo("yofoys_bean","currency");
    }

    private static ModleInfo areaProxy(ProInfo project){
        //对应dbconfig中的name：ssweixin、yofoysAreaProxy、yofoysContent、yofoys_activities、currency
        project.setDatabaseName("yofoysAreaProxy");
        //${yofoys.modules.center-areaproxy}
        project.setModuleServiceName("${yofoys.modules.center-areaproxy}");
        project.setDelTablePrefix("areap_");

        project.setOutName("test-build");
        //表所在数据库
        return new ModleInfo("yofoys_Area_Proxy","areaproxy");
    }


    private static ModleInfo log(ProInfo project){
        //对应dbconfig中的name：
        project.setDatabaseName("dblog");
        //${yofoys.modules.center-areaproxy}
        project.setModuleServiceName("${yofoys.modules.service-log}");
        project.setDelTablePrefix("");

        project.setOutName("test-build");
        //表所在数据库
        return new ModleInfo("yofoys_log","logs");
    }

    private static ModleInfo docArchive(ProInfo project){
        //对应dbconfig中的name：ssweixin、yofoysAreaProxy、yofoysContent、yofoys_activities、currency
        project.setDatabaseName("ssweixin");
        project.setModuleServiceName("${yofoys.modules.assist-docArchive}");
        project.setDelTablePrefix("");

        project.setOutName("test-build");
        //表所在数据库
        return new ModleInfo("doc_archive","docarchive");
    }

}

class ModleInfo{
    //表所在数据库:ssweixin、yofoys_Area_Proxy、、、
    private String dbName="ssweixin";
    private String module="";//模块名，模块包子包名：areaproxy、pay

    public ModleInfo() {}
    public ModleInfo(String dbName, String module) {
        this.dbName = dbName;
        this.module = module;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }
}
