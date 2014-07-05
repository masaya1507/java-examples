package jp.proj.miya.java_examples.json;

import java.util.Collection;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

import com.jayway.jsonpath.JsonModel;

/**
 * 
 * @author partner_m
 *
 */
public class JsonPathParser {
	/**  */
	private static final String JSON_PATH_ROOT = "$";
	
	/**
	 * 
	 * @param json
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public <T extends Collection<String>> T getJsonPath(String json, Class<T> clazz) throws InstantiationException, IllegalAccessException {
		JsonModel model = JsonModel.create(json);
		
		Object dat = model.getJsonObject();

		T collection = clazz.newInstance();

		if (dat instanceof JSONObject) {
			this.storeJsonPath((JSONObject) dat, collection, JsonPathParser.JSON_PATH_ROOT);
		} else if (dat instanceof JSONArray) {
			this.storeJsonPath((JSONArray) dat, collection, JsonPathParser.JSON_PATH_ROOT);
		}

		return collection;
	}

	/**
	 * 
	 * @param jsonObjectParent
	 * @param list
	 * @param parent
	 */
	protected void storeJsonPath(JSONObject jsonObjectParent, Collection<String> list, String parent) {
		for (String key : jsonObjectParent.keySet()) {
			Object node = jsonObjectParent.get(key);
			String jsonPath = parent + "." + key;

			if (node instanceof JSONObject) {
				this.storeJsonPath((JSONObject) node, list, jsonPath);
			} else if (node instanceof JSONArray) {
				this.storeJsonPath((JSONArray) node, list, jsonPath);
			} else {
				list.add(jsonPath);
			}
		}
	}

	/**
	 * 
	 * @param jsonObjectParent
	 * @param list
	 * @param parent
	 */
	protected void storeJsonPath(JSONArray jsonObjectParent, Collection<String> list, String parent) {
		for (int i = 0; i < jsonObjectParent.size(); i++) {
			String jsonPath = parent + "[" + i + "]";
			
			if (jsonObjectParent.get(i) instanceof JSONObject) {
				this.storeJsonPath((JSONObject) jsonObjectParent.get(i), list, jsonPath);
			} else if (jsonObjectParent.get(i) instanceof JSONArray) {
				this.storeJsonPath((JSONArray) jsonObjectParent.get(i), list, jsonPath);
			} else {
				list.add(jsonPath);
			}
		}
	}
}
