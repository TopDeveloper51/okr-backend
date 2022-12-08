package com.apis.okre.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {
	public static final JSONObject getResponseObject( String msg, List<?> data) {
		JSONObject resObj = new JSONObject();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode node = objectMapper.valueToTree(data);
		resObj.put("message", msg);
		resObj.put("data",new JSONArray(node.toString()));
		return resObj;
	}
	
	public static final JSONObject getResponseObjectError( String msg, List<FieldError> data) {
		JSONObject resObj = new JSONObject();
		ObjectMapper objectMapper = new ObjectMapper();
		List<String> resData = new ArrayList<String>();
		
		for(FieldError item :data) {
			resData.add(item.getDefaultMessage());
		}
		
		JsonNode node = objectMapper.valueToTree(resData);
		resObj.put("message", msg);
		resObj.put("data",new JSONArray(node.toString()));
		return resObj;
	}
	
	public static final JSONObject getResponseObject( String msg, JsonNode data) {
		JSONObject resObj = new JSONObject();
		resObj.put("message", msg);
		resObj.put("data",new JSONObject(data.toString()));
		return resObj;
	}
	public static final JSONObject getResponseObject( String msg) {
		JSONObject resObj = new JSONObject();
		resObj.put("message", msg);
		return resObj;
	}
	public static final JSONObject getResponseObject( String msg, Long data) {
		JSONObject resObj = new JSONObject();
		resObj.put("message", msg);
		resObj.put("data",data);
		return resObj;
	}
	public static final JSONObject getResponseObject( String msg, int data) {
		JSONObject resObj = new JSONObject();
		resObj.put("message", msg);
		resObj.put("data",data);
		return resObj;
	}
	public static final JSONObject getResponseObject( String msg, JSONObject data) {
		JSONObject resObj = new JSONObject();
		resObj.put("message", msg);
		resObj.put("data",data);
		return resObj;
	}
	public static final JSONObject getResponseObject( String msg, Object data) {
		JSONObject resObj = new JSONObject();
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode node = objectMapper.valueToTree(data);
		resObj.put("message", msg);
		resObj.put("data",new JSONObject(node.toString()));
		return resObj;
	}
}
