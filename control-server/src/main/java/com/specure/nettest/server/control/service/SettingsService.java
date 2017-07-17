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

package com.specure.nettest.server.control.service;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.specure.nettest.server.control.exception.SettingsNotFoundException;
import com.specure.nettest.shared.model.Settings;
import com.specure.nettest.spring.data.couchdb.repository.CouchDbRepository;

/**
 * 
 * @author Specure GmbH (bp@specure.com)
 *
 */
@Service
public class SettingsService {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(SettingsService.class);
	
	/**
	 * 
	 */
	@Inject
	private CouchDbRepository<Settings> settingsRepository;
	
	/**
	 * 
	 */
	public SettingsService() {

	}
	
	/**
	 * 
	 * @param settingsKey
	 * @return
	 */
	//@Cacheable(value = "settings", key = "'settings_' + #settingsKey")
	public Settings getSettings(String settingsKey) {
		final Settings settings = settingsRepository.findOne(settingsKey);

		if (settings == null) {
			throw new SettingsNotFoundException("Could not find settings with key '" + settingsKey + "'");
		}
		
		// remove advertised speed config if not enabled (leaves advertiesSpeeds: [] in json...)
		if (!settings.isAdvertisedSpeedsEnabled()) {
			settings.getAdvertisedSpeeds().clear();
		}
		
		return settings;
	}
}
