/*******************************************************************************
 * Copyright 2016-2017 alladin-IT GmbH
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package at.alladin.nettest.shared.server.helper;

import java.util.Arrays;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.specure.nettest.shared.helper.GsonBasicHelper;




/**
 * Helper to group requested values of a given JSON
 * @author fk
 *
 */
public class JsonGroupResultsHelper {
	
	/**
	 * Groups the results according to the groupStructure param
	 * Will move all values that are defined in the groupStructure from their current location into the location defined by the groupStructure
	 * If none of the defined values of a group within the groupStructure are valid, the groupStructure will NOT be added at all
	 * If an exception is thrown during this process, the original json String is returned
	 * @param json, the JSON result to be grouped
	 * @param groupStructure, the structure to be applied to the json String as obtained w/ServerResource.getSetting(speedtestDetailGroups) TODO: make this more specific
	 * @return a JSONObject that has all key/value pairs that are defined in the groupStructure moved to the correct place, all other values remain unchanged
	 */
	public static JsonObject groupJsonResult(String json, JsonArray groupStructure){
		final Gson gson = GsonBasicHelper.getDateTimeGsonBuilder().create();
		final JsonObject jsonObj = gson.fromJson(json, JsonObject.class);

		jsonObj.add("groups", new JsonArray());
		for (int i = 0; i < groupStructure.size(); i++) {
			extractGroupValues(jsonObj, groupStructure.get(i).getAsJsonObject());
		}
		return jsonObj;
	}
	
	/**
	 * Helper method to extract all values specified in the groupDefinition from the json JSONObject
	 * places them into the defined groups instead
	 * @param json the JSONObject containing all the information to be grouped
	 * @param groupDefinition the definition of a single group to be extracted by this method
	 * @return the same json with the specified values moved into the specified groups
	 */
	private static JsonObject extractGroupValues(JsonObject json, JsonObject groupDefinition){
		boolean firstValue = true;
		JsonObject currentGroup = new JsonObject();
		final JsonArray valuesArray = groupDefinition.getAsJsonArray("values");
		
		for(int i = 0; i < valuesArray.size(); i++){
			final String[] keyPath = valuesArray.get(i).getAsJsonObject().get("key").getAsString().split("\\.");
			if(keyPath.length == 0){
				continue;
			}
			final String lastKey = keyPath[keyPath.length - 1];
			//the object used to navigate to the specified key
			JsonObject navigationObj = json;
			for(String s : keyPath){
				if(navigationObj.has(s) && navigationObj.get(s).isJsonObject()){
					navigationObj = navigationObj.getAsJsonObject(s);
				}
			}
			if(!navigationObj.has(lastKey)){
				continue;
			}else{
				if(firstValue){
					appendGroupStartDefinition(currentGroup, groupDefinition);
					firstValue = false;
				}
				final JsonObject valueEntry = new JsonObject();
				valueEntry.add("key", new JsonPrimitive(lastKey));
				valueEntry.add("value", navigationObj.get(lastKey));
				//aditionally put everything else we find in the structure into the json obj (meant for things like format strings or units
				for(Map.Entry<String, JsonElement> elem : valuesArray.get(i).getAsJsonObject().entrySet()){
					if(elem.getKey().equals("key")){
						continue;
					}
					valueEntry.add(elem.getKey(), elem.getValue());
				}
				//the default translation key is "key_" + the lastKey
				if(!valueEntry.has("translation_key")){
//					JsonPrimitive trans = new JsonPrimitive("key_" + lastKey);
					valueEntry.add("translation_key", new JsonPrimitive("key_" + lastKey));
				}
				cleanKeyRemove(json, navigationObj, keyPath);
				currentGroup.getAsJsonArray("values").add(valueEntry);
			}
		}
		if(!firstValue){
			json.getAsJsonArray("groups").add(currentGroup);
		}
		return json;
	}
	
	/**
	 * Helper method to remove not only the specified entry but the complete JSONObject if that was the last entry of that JSONObject
	 * @param fullJsonObj the complete json object
	 * @param navigationObj the json object where the key to-be-deleted is located
	 * @param keyPath full path of the to-be-deleted key
	 * @return the fullJsonObj w/out the specified key and w/out empty JSONObjs
	 */
	private static JsonObject cleanKeyRemove(JsonObject fullJsonObj, JsonObject navigationObj, String[] keyPath){
		navigationObj.remove(keyPath[keyPath.length -1]);
		if(!navigationObj.entrySet().isEmpty()){
			return fullJsonObj;
		}
		navigationObj = fullJsonObj;
		for(int i = 0; i < keyPath.length - 2; i++){
			navigationObj = navigationObj.has(keyPath[i]) ? navigationObj.getAsJsonObject(keyPath[i]) : navigationObj;
		}
		cleanKeyRemove(fullJsonObj, navigationObj, Arrays.copyOf(keyPath, keyPath.length - 1));
		return fullJsonObj;
	}
	
	/**
	 * Helper method to append the group definition from the groupStructure (e.g. to append the key and icon of the group)
	 * Simply appends everything it finds apart from the values themselves
	 * @param json the json to append to
	 * @param groupToAppend the group data to be appended
	 * @return the original json with the starting group info appended
	 */
	private static JsonObject appendGroupStartDefinition(JsonObject jsonObj, JsonObject groupToAppend){
		for(Map.Entry<String, JsonElement> elem : groupToAppend.entrySet()){
			if(elem.getKey().equals("values")){
				continue;
			}
			jsonObj.add(elem.getKey(), elem.getValue());
		}
		jsonObj.add("values", new JsonArray());
		return jsonObj;
	}

}
