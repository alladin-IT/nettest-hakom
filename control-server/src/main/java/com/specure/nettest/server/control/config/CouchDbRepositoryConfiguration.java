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

import org.lightcouch.CouchDbClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.specure.nettest.shared.model.Client;
import com.specure.nettest.shared.model.Device;
import com.specure.nettest.shared.model.Measurement;
import com.specure.nettest.shared.model.MeasurementServer;
import com.specure.nettest.shared.model.NetworkType;
import com.specure.nettest.shared.model.Provider;
import com.specure.nettest.shared.model.Settings;
import com.specure.nettest.shared.model.Translation;
import com.specure.nettest.shared.model.qos.QosMeasurementObjective;
import com.specure.nettest.spring.data.couchdb.repository.CouchDbRepository;
import com.specure.nettest.spring.data.couchdb.repository.LightCouchCouchDbRepository;

/**
 * 
 * @author Specure GmbH (bp@specure.com)
 *
 */
@Configuration
@AutoConfigureAfter({
	CouchDbConfiguration.class
})
public class CouchDbRepositoryConfiguration {
	
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(CouchDbRepositoryConfiguration.class);
	
	/////////////////////////////////////
	// Measurement
	/////////////////////////////////////
	
	/**
	 * 
	 * @param measurementDbClient
	 * @return
	 */
	@Bean
	public CouchDbRepository<Client> clientRepository(CouchDbClient measurementDbClient) {
		return new LightCouchCouchDbRepository<>(Client.class, measurementDbClient);
	}
	
	/**
	 * 
	 * @param measurementDbClient
	 * @return
	 */
	@Bean
	public CouchDbRepository<Measurement> measurementRepository(CouchDbClient measurementDbClient) {
		return new LightCouchCouchDbRepository<>(Measurement.class, measurementDbClient);
	}
	
	/////////////////////////////////////
	// Translation
	/////////////////////////////////////
	
	/**
	 * 
	 * @param translationDbClient
	 * @return
	 */
	@Bean
	public CouchDbRepository<Translation> translationRepository(CouchDbClient translationDbClient) {
		return new LightCouchCouchDbRepository<>(Translation.class, translationDbClient);
	}
	
	/////////////////////////////////////
	// Configuration
	/////////////////////////////////////
	
	/**
	 * 
	 * @param configurationDbClient
	 * @return
	 */
	@Bean
	public CouchDbRepository<Device> deviceRepository(CouchDbClient configurationDbClient) {
		return new LightCouchCouchDbRepository<>(Device.class, configurationDbClient);
	}
	
	/**
	 * 
	 * @param configurationDbClient
	 * @return
	 */
	@Bean
	public CouchDbRepository<Provider> providerRepository(CouchDbClient configurationDbClient) {
		return new LightCouchCouchDbRepository<>(Provider.class, configurationDbClient);
	}
	
	/**
	 * 
	 * @param configurationDbClient
	 * @return
	 */
	@Bean
	public CouchDbRepository<MeasurementServer> measurementServerRepository(CouchDbClient configurationDbClient) {
		return new LightCouchCouchDbRepository<>(MeasurementServer.class, configurationDbClient);
	}
	
	/**
	 * 
	 * @param configurationDbClient
	 * @return
	 */
	@Bean
	public CouchDbRepository<Settings> settingsRepository(CouchDbClient configurationDbClient) {
		return new LightCouchCouchDbRepository<>(Settings.class, configurationDbClient);
	}
	
	/**
	 * 
	 * @param networkTypesRepository
	 * @return
	 */
	@Bean
	public CouchDbRepository<NetworkType> networkTypeRepository(CouchDbClient configurationDbClient) {
		return new LightCouchCouchDbRepository<>(NetworkType.class, configurationDbClient);
	}
	
	///////////
	// Qos
	
	/**
	 * 
	 * @param configurationDbClient
	 * @return
	 */
	@Bean
	public CouchDbRepository<QosMeasurementObjective> measurementObjectiveRepository(CouchDbClient configurationDbClient) {
		return new LightCouchCouchDbRepository<>(QosMeasurementObjective.class, configurationDbClient);
	}
}
