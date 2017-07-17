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

package com.specure.nettest.server.control.web.api.v1;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.specure.nettest.server.control.config.ControlServerProperties;
import com.specure.nettest.server.control.service.ClientService;
import com.specure.nettest.server.control.service.SettingsService;
import com.specure.nettest.shared.model.Client;
import com.specure.nettest.shared.model.Settings;
import com.specure.nettest.shared.model.qos.QosMeasurementType;
import com.specure.nettest.shared.model.request.SettingsRequest;
import com.specure.nettest.shared.model.response.SettingsResponse;
import com.specure.nettest.shared.model.response.SettingsResponse.QosMeasurementTypeResponse;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * @author lb@specure.com
 * @author Specure GmbH (bp@specure.com)
 *
 */
@RestController
@RequestMapping("/api/v1/settings")
public class SettingsResource {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(SettingsResource.class);
	
	/**
	 * 
	 */
	@Inject
	private ControlServerProperties controlServerProperties;
	
	/**
	 * 
	 */
	@Inject
	private SettingsService settingsService;
	
	/**
	 * 
	 */
	@Inject
	private ClientService clientService;
	
	/**
	 * 
	 */
	@Inject
	private MessageSource messageSource;
	
	/**
	 * 
	 * @param client
	 * @param locale
	 * @return
	 */
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "lang", dataType = "string", paramType = "query", value = "Accept-Language override") // Locale
	})
	
	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	//@Cacheable(value = "settings", key = "'settings_' + #locale.toString()") // TODO: cache settings request for each locale and client uuid? or just cache parts?
    public ResponseEntity<SettingsResponse> getSettings(@RequestBody SettingsRequest request, @ApiIgnore Locale locale) {
		// for backwards compatibility: register client if no client uuid is submitted
		if (request.getClient() == null) {
			request.setClient(new Client());
		}

		if (!clientService.isClientRegistered(request.getClient().getUuid())) {
			clientService.registerNewClient(request.getClient());
		}
		
		// get settings from database
		final Settings settings = settingsService.getSettings(controlServerProperties.getSettingsKey());
		
		// add qos measurement types with localized names
		final List<QosMeasurementTypeResponse> qosMeasurementTypes = new ArrayList<>();
		QosMeasurementType.CONSTANTS.forEach((k, v) -> {
			final String translatedName = messageSource.getMessage(v.getNameKey(), null, locale);
			final String translatedDescription = messageSource.getMessage(v.getDescriptionKey(), null, locale);
			
			qosMeasurementTypes.add(new QosMeasurementTypeResponse(v, translatedName, translatedDescription));
		});
		
		// TODO: add history (devices, networks) (queries sync groups) [-> FILTER ON CLIENT?]
		
		// TODO: add versions
		settings.getVersions().setControlServerVersion("1.0.0");
		
		final SettingsResponse settingsResponse = new SettingsResponse();
		settingsResponse.setSettings(settings);
		settingsResponse.setQosMeasurementTypes(qosMeasurementTypes);
		settingsResponse.setClient(request.getClient());
		
		return new ResponseEntity<>(settingsResponse, HttpStatus.OK);
	}
}
