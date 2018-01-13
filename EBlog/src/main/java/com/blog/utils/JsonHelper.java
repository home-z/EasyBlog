package com.blog.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * json帮助类
 * 
 * @author Administrator
 * 
 */
public class JsonHelper {
	
	/**
	 * 根据集合返回Map集合，返回到前端，使用spring mvc+jackson自动转换成json
	 * 
	 * @param list
	 * @return Map<String, Object>
	 */
	public static <E> Map<String, Object> getModelMap(List<E> list) {
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		modelMap.put("total", list.size());
		modelMap.put("data", list);
		modelMap.put("success", "true");
		
		return modelMap;
	}

	public static <E> Map<String, Object> getModelMapforGrid(List<E> list) {
		Map<String, Object> modelMap = new HashMap<String, Object>(2);
		modelMap.put("total", list.size());
		modelMap.put("rows", list);
		
		return modelMap;
	}

	public static <E> Map<String, Object> getModel(E model) {
		Map<String, Object> modelMap = new HashMap<String, Object>(3);
		modelMap.put("total", 1);
		modelMap.put("data", model);
		modelMap.put("success", "true");
		
		return modelMap;
	}

	/**
	 * 返回是否成功结果
	 * 
	 * @param isSucess
	 * @return
	 */
	public static Map<String, String> getSucessResult(boolean isSucess) {
		Map<String, String> modelMap = new HashMap<String, String>(1);
		if (isSucess) {
			modelMap.put("success", "true");
		} else {
			modelMap.put("success", "false");
		}

		return modelMap;
	}
	
	/**
	 * 返回是否成功结果以及详细信息
	 * 
	 * @param isSucess
	 * @return
	 */
	public static Map<String, String> getSucessResult(boolean isSucess,String content) {
		Map<String, String> modelMap = new HashMap<String, String>(2);
		if (isSucess) {
			modelMap.put("success", "true");
		} else {
			modelMap.put("success", "false");
		}
		modelMap.put("content", content);

		return modelMap;
	}

	/**
	 * 将string转换成listBean
	 * 
	 * @param jsonArrStr
	 *            需要反序列化的字符串
	 * @param clazz
	 *            被反序列化之后的类
	 * @return 实体list
	 */
	@SuppressWarnings("unchecked")
	public static List getListFromJsonArrStr(String jsonArrStr, Class clazz) {
		JSONArray jsonArr = JSONArray.fromObject(jsonArrStr);
		List list = new ArrayList();
		for (int i = 0; i < jsonArr.size(); i++) {
			list.add(JSONObject.toBean(jsonArr.getJSONObject(i), clazz));
		}
		return list;
	}

}
