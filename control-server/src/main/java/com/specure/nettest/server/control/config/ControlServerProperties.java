/*******************************************************************************
 * Copyright 2016-2017 alladin-IT GmbH
 * Copyright 2016 SPECURE GmbH
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

package com.specure.nettest.server.control.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 
 * @author Specure GmbH (bp@specure.com)
 *
 */
@ConfigurationProperties(prefix = "controlServer", ignoreUnknownFields = false)
public class ControlServerProperties {

	/**
	 * 
	 */
	private final List<String> rmbtClientName = new ArrayList<>();
	
	/**
	 * 
	 */
	private final List<String> rmbtVersionNumber = new ArrayList<>();
	
	/**
	 * 
	 */
	private String defaultLocale;
	
	/**
	 * 
	 */
	private String settingsKey;

	/**
	 * 
	 * @return
	 */
	public String getDefaultLocale() {
		return defaultLocale;
	}

	/**
	 * 
	 * @param defaultLocale
	 */
	public void setDefaultLocale(String defaultLocale) {
		this.defaultLocale = defaultLocale;
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getRmbtClientName() {
		return rmbtClientName;
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getRmbtVersionNumber() {
		return rmbtVersionNumber;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getSettingsKey() {
		return settingsKey;
	}
	
	/**
	 * 
	 * @param settingsKey
	 */
	public void setSettingsKey(String settingsKey) {
		this.settingsKey = settingsKey;
	}
}
