package com.pck.base.webmain.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class APIResponse {
	
	public static final String KEY_ERROR = "error";
	public static final String KEY_ERRORS = "errors";
	
	public static final String MESSAGE_MULTIPLE_ERRORS = "Multiple errors, check 'errors'";
	
	private Map<String, Object> responseMap = new HashMap<String, Object>();
	
	private APIResponse() {
		
	}
	
	private APIResponse(String key, String value) {
		responseMap.put(key, value);
	}
	
	public static APIResponse newInstance() {
		return new APIResponse();
	}
	
	public static APIResponse newError(String error) {
		return new APIResponse(KEY_ERROR, error);
	}
	
	@SuppressWarnings("unchecked")
	public APIResponse addError(String error) {
		if (!responseMap.containsKey(KEY_ERROR)) {
			responseMap.put(KEY_ERROR, error);
			return this;
		} else if (!responseMap.containsKey(KEY_ERRORS)) {
			responseMap.put(KEY_ERRORS, new ArrayList<String>());			
			((ArrayList<String>)responseMap.get(KEY_ERRORS)).add((String)responseMap.get(KEY_ERROR));
			responseMap.put(KEY_ERROR, MESSAGE_MULTIPLE_ERRORS);
		}
		((ArrayList<String>)responseMap.get(KEY_ERRORS)).add(error);
		return this;
	}
	
	public Map<String, Object> getResponseMap() {
		return this.responseMap;
	}
}
