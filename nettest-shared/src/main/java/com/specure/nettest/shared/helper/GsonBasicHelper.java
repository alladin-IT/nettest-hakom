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

package com.specure.nettest.shared.helper;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.GsonBuilder;


public class GsonBasicHelper {
	
	public static GsonBuilder getDateTimeGsonBuilder() {
		final GsonBuilder gsonBuilder = new GsonBuilder()
                .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'"); // untested; though this is the standard JavaScript.toJSON() syntax, we use joda-time.
        
		// register joda datetime
    	Converters.registerDateTime(gsonBuilder);
    	
		return gsonBuilder;
	}
	
	
}
