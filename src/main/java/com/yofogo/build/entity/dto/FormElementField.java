package com.yofogo.build.entity.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yofogo.build.entity.po.FormElementFields;

public class FormElementField extends FormElementFields implements java.io.Serializable,Cloneable{

	private String dataTag;
	private String dbType;
	private Integer isMust;
	private Integer mgStatus;
	private SelectItems selectItem;
	private Integer fdfMust=0;

	public String getDataTag() {
		return dataTag;
	}

	public void setDataTag(String dataTag) {
		this.dataTag = dataTag;
	}
	public String getDbType() {
		return dbType.toUpperCase();
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	public Integer getIsMust() {
		return isMust;
	}
	public void setIsMust(Integer isMust) {
		this.isMust = isMust;
	}
	public Integer getFdfMust() {
		return fdfMust;
	}
	public void setFdfMust(Integer fdfMust) {
		this.fdfMust = fdfMust;
	}
	public Integer getMgStatus() {
		return mgStatus;
	}
	public void setMgStatus(Integer mgStatus) {
		this.mgStatus = mgStatus;
	}
	public SelectItems getSelectItem() {
		//if(selectItem==null) getSelectItemsVals();
		return selectItem;
	}


	public String dbTypeToType(){
		/*if("varchar".equalsIgnoreCase(this.dbType) || "vchar".equalsIgnoreCase(this.dbType)){
			if(this.len==null || this.len<512) return FormFieldType.TEXT.getValue();
			else return FormFieldType.TEXTAREA.getValue();
		} else if("int".equalsIgnoreCase(this.dbType) || "smallint".equalsIgnoreCase(this.dbType) || "bigint".equalsIgnoreCase(this.dbType)){
			return FormFieldType.NUMBER.getValue();
		} else if("decimal".equalsIgnoreCase(this.dbType) || "numeric".equalsIgnoreCase(this.dbType)
				|| "double".equalsIgnoreCase(this.dbType) || "float".equalsIgnoreCase(this.dbType)){
			return FormFieldType.DOUBLE.getValue();
		} else if("datetime".equalsIgnoreCase(this.dbType)){
			return FormFieldType.DATETIME.getValue();
		} else if("date".equalsIgnoreCase(this.dbType)){
			return FormFieldType.DATE.getValue();
		} else if("text".equalsIgnoreCase(this.dbType)){
			return FormFieldType.EDITOR.getValue();
		}  else return FormFieldType.TEXT.getValue();*/
		return "";
	}
	////////////////////////////////////////////////
	/*
	
	public List<Map<String,Object>> getSelectItemsVals() {
		if(selectItems==null || "".equals(selectItems.trim())) return new ArrayList<Map<String,Object>>();
		JSONObject json=JSONObject.parseObject(selectItems);
		this.selectItem=new SelectItems();
		int type=json.getIntValue("type");
		selectItem.setType(type);
		List<Map<String,Object>> items=new ArrayList<Map<String,Object>>();
		if(type==1){
			JSONArray datas=json.getJSONArray("datas");
			if(datas==null) return items;
			if(this.selectItemsVals!=null) return this.selectItemsVals; 
			for(Object obj : datas){
				items.add((Map<String,Object>)obj);
			}
			selectItem.setItems(items);
		}else {
			String sql=json.getString("datas");
			int firstIndex=sql.indexOf("#{");
			if(firstIndex>0){
				String regex="(( *(and|or|AND|OR))? +\\w+ *\\= *\\#\\{((?!\\#\\{).)+ *\\})";
				String regexC="( *(and|or|AND|OR))? +(\\w+) *\\= *\\#\\{(.*)\\}";
				Pattern p =Pattern.compile(regex);
				Matcher m=p.matcher(sql);
				while(m.find()){
					String groupStr=m.group(0);
					Matcher mc=Pattern.compile(regexC).matcher(groupStr);
					if(mc.find()){
						String joinStr=mc.group(1);
						if(joinStr==null) joinStr=" ";
						String field=mc.group(3);
						String val=mc.group(4);
						sql=sql.replace(groupStr, joinStr+" "+field+"=?");
						selectItem.getParamNames().add(field);
						selectItem.getParams().put(field,val);
					}
				}
			}
			selectItem.setSql(sql);
			try {
				Object[][] objs=formMgServices.findSelectItems(sql);
				if(objs!=null && objs.length>0){
					for(Object[] obj : objs){
						Map<String,Object> item=new HashMap<String,Object>();
						item.put("val", obj[0]+"");
						item.put("name", obj[1]+"");
						items.add(item);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return items;
	}*/
	
	@Override
	public FormElementField clone()  {
		try {
			return (FormElementField)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		String sql="select id ,name from tableN where age=#{$('#city').val()}  and (1==2 OR age1=#{$('#item').val()})   AND   age2 = #{$('#item').val() }";
		String regex="(( *(and|or|AND|OR))? +\\w+ *\\= *\\#\\{((?!\\#\\{).)+ *\\})";
		Pattern p =Pattern.compile(regex);
		Matcher m=p.matcher(sql);
		while(m.find()){
			String groupStr=m.group(0);
			System.out.println(groupStr);
			Matcher mc=Pattern.compile("( *(and|or|AND|OR))? +(\\w+) *\\= *\\#\\{(.*)\\}").matcher(groupStr);
			if(mc.find()){
				String joinStr=mc.group(1);
				if(joinStr==null) joinStr=" ";
				String field=mc.group(3);
				String val=mc.group(4);
				sql=sql.replace(groupStr, joinStr+" "+field+"=?");
				System.out.println(field+"="+val);
			}
		}
		System.out.println(sql);
	}
	
}
