package com.yzz.cms.build;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yofogo.build.entity.dto.*;
import com.yofogo.build.entity.enums.BuildConmand;
import com.yofogo.build.entity.enums.DBFieldType;
import com.yofogo.build.entity.enums.DtoType;
import com.yofogo.build.entity.enums.FormFieldType;
import com.yofogo.build.entity.po.ProInfo;
import com.yofogo.build.generator.IGenerator;
import com.yofogo.build.generator.impl.GeneratorYofoysServiceImpl;

import cn.com.yofogo.tools.util.DateTimeUtil;
import org.apache.commons.lang3.StringUtils;

public class BuildHandler {
	private static boolean isAnnotation=true;
	private static boolean isAnnotationDesc=true,isAnnotationDto=true;
	private static boolean isLombok=false;
	private static boolean isDto=true;
	private static String fileCharSetName="UTF-8";



	public static boolean buildConmand(Set<BuildConmand> conmands, ProInfo project, FormInfo formInfo){
		if(conmands==null || "".equals(conmands) || formInfo==null) return false;
		IGenerator generator = new GeneratorYofoysServiceImpl();
		List<String> methods =new ArrayList();
		methods.add("add");
		methods.add("update");
		Iterator<BuildConmand> iterable = conmands.iterator();
		while (iterable.hasNext()) {
			BuildConmand com = iterable.next();
			if(BuildConmand.ENTITY == com){
				if(!generator.buildEntity(project, formInfo)) return false;
			} else if(BuildConmand.DAO==com){
				if(!generator.buildDas(project, formInfo, methods)) return false;
			} else if(BuildConmand.BIZ==com) {
				if(!generator.buildBizAndImpl(project, formInfo)) return false;


			} /*else if("pageView".equals(com)){
				if(!buildHTML(project, formInfo,methods)) return false;
			} else if("project".equals(com)){
				if(!buildProjectBase(project)) return false;
			}*/
		}
		return false;
	}


}
