package com.pck.base.webmain.common;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class Util {
	
	private final static Logger logger = LoggerFactory.getLogger(Util.class);
	
	public static Gson gson = new Gson();

	public static String toGson(Object data) {
		logger.debug("toGson(Object);");
		return gson.toJson(data);
	}
	
	public static String toGson(APIResponse apiResponse) {
		logger.debug("toGson(APIResponse);");
		return gson.toJson(apiResponse.getResponseMap());
	}
	
	public static String toGson(Object data, APIResponse apiResponse) {
		logger.debug("toGson(Object, APIResponse);");
		if (data == null) {
			return toGson(apiResponse);
		} else if (apiResponse == null) {
			return toGson(data);
		}
		Map<String, Object> envelope = new HashMap<String, Object>();
		envelope.put("output", data);
		envelope.put("apiResponse", apiResponse.getResponseMap());
		return gson.toJson(envelope);
	}
}
