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

import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.specure.nettest.server.control.service.ClassificationService;
import com.specure.nettest.server.control.service.ClassificationService.ClassificationType;
import com.specure.nettest.server.control.service.ClientService;
import com.specure.nettest.server.control.service.DeviceService;
import com.specure.nettest.server.control.service.MeasurementService;
import com.specure.nettest.shared.model.Client;
import com.specure.nettest.shared.model.Device;
import com.specure.nettest.shared.model.HistoryItem;
import com.specure.nettest.shared.model.Measurement;
import com.specure.nettest.shared.model.MeasurementClientInfo;
import com.specure.nettest.shared.model.MeasurementSpeedtest;

import at.alladin.rmbt.shared.SignificantFormat;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * @author lb@specure.com
 * @author Specure GmbH (bp@specure.com)
 *
 */
@RestController
@RequestMapping("/api/v1/clients")
public class ClientResource {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(ClientResource.class);
	
	/**
	 * 
	 */
	@Inject
	private ClientService clientService;
	
	/**
	 * 
	 */
	@Inject
	private ClassificationService classificationService;
	
	/**
	 * 
	 */
	@Inject
	private DeviceService deviceService;
	
	/**
	 * 
	 */
	@Inject
	private MeasurementService measurementService;
	
	/**
	 * Registers a new client. 
	 * 
	 * Returns 201 OK if the client was registered successfully.
	 * Returns 400 BAD REQUEST if the client hasn't accepted the terms and conditions.
	 * Returns 409 CONFLICT if the client is already stored in our database.
	 * 
	 * @return
	 */
	@ApiOperation(value = "Registers a client", notes = "Registers a client and returns the client object with the generated uuid.", response = Client.class)
	@ApiResponses({
		@ApiResponse(code = 201, message = "Created", response = Client.class)
	})
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "lang", dataType = "string", paramType = "query", value = "Accept-Language override") // Locale
	})

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerClient(@RequestBody Client client, @ApiIgnore Locale locale) {
		clientService.registerNewClient(client);
		
		return new ResponseEntity<>(client, HttpStatus.CREATED);
	}
	
	/**
	 * Fetches the user's measurements, but with few details only. 
	 * 
	 * Returns 200 OK if the request was processed successfully.
	 * Returns 403 FORBIDDEN if the client is not registered.
	 *
	 * @param clientUuid
	 * @param pageable
	 * @param locale
	 * @return
	 */
	@ApiImplicitParams({
		// Swagger <--> Pageable, see http://stackoverflow.com/questions/35404329/swagger-documentation-for-spring-pageable-interface
	    /*
		@ApiImplicitParam(name = "page", dataType = "integer", paramType = "query", value = "Results page you want to retrieve (0..N)"),
	    @ApiImplicitParam(name = "size", dataType = "integer", paramType = "query", value = "Number of records per page."),
	    @ApiImplicitParam(name = "sort", allowMultiple = true, dataType = "string", paramType = "query", value = "Sorting criteria in the format: property(,asc|desc). Default sort order is ascending. Multiple sort criteria are supported."),
		*/
	
	    // Locale
	    @ApiImplicitParam(name = "lang", dataType = "string", paramType = "query", value = "Accept-Language override")
	})
	
	@RequestMapping(value = "/{clientUuid}/measurements", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMeasurementList(@PathVariable String clientUuid, @RequestParam(required=false, defaultValue="0") Long timestamp/*, @ApiIgnore Pageable pageable*/, @ApiIgnore Locale locale) {
		clientService.ensureClientRegistered(clientUuid);
		
		final List<Measurement> measurementPage = clientService.getMeasurementListByDate(clientUuid, true, false/*, pageable*/);
		
		final Format format = new SignificantFormat(2, locale);
		
		final List<HistoryItem> historyList = new ArrayList<>();
		
		for (final Measurement m : measurementPage) {
			final HistoryItem h = new HistoryItem();
			h.setTestUuid(m.getUuid());
			
			final MeasurementClientInfo clientInfo = m.getClientInfo();
			final long measurementTimestamp = clientInfo.getTime().getMillis();
			
			if (measurementTimestamp > timestamp) {
				final MeasurementSpeedtest speedtest = m.getSpeedtest();

				final long speedDownload = speedtest.getSpeedDownload();
				final long speedUpload = speedtest.getSpeedUpload();
				final long pingMedian = speedtest.getPingMedian();
				final long pingShortest = speedtest.getPingShortest();
				
				h.setTime(measurementTimestamp);
				h.setTimeZone(clientInfo.getTimezone());
				h.setTimeString(clientInfo.getTime().toString());
				
				h.setSpeedDownload(format.format(speedDownload / 1000d));
				h.setSpeedUpload(format.format(speedUpload / 1000d));
				
				h.setPing(format.format(pingMedian / 1000000d));
				h.setPingShortest(format.format(pingShortest / 1000000d));
				
				if (m.getDeviceInfo() != null) {
					final Device device = deviceService.findByCodename(m.getDeviceInfo().getModel());
					h.setModel(device != null ? device.getFullname() : m.getDeviceInfo().getModel());
				}
				else {
					h.setModel(""); //empty string?
				}
				
				h.setSpeedUploadClassification(classificationService.classify(ClassificationType.UPLOAD, speedUpload));
				h.setSpeedDownloadClassification(classificationService.classify(ClassificationType.DOWNLOAD, speedDownload));
				h.setPingClassification(classificationService.classify(ClassificationType.PING, pingMedian));
				h.setPingShortClassification(classificationService.classify(ClassificationType.PING, pingShortest));
				h.setNetworkType(m.getNetworkInfo().getNetworkGroupName());
				
				h.setQosResultAvailable(m.getQos() != null);
			}
			
			historyList.add(h);
		}
		
		return new ResponseEntity<>(historyList, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/{clientUuid}/measurements/{uuid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> disassociateSpeedMeasurement(@PathVariable String clientUuid, @PathVariable String uuid) {
		
		// ensure that client is registered
		clientService.ensureClientRegistered(clientUuid);
		
		measurementService.disassociateMeasurement(clientUuid, uuid);
		
		final Map<String, Object> result = new HashMap<>();
		result.put("success", true);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param locale
	 * @return
	 */
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "lang", dataType = "string", paramType = "query", value = "Accept-Language override") // Locale
	})
	
	@RequestMapping(value = "/sync" /* sync-code?*/, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getSync(Locale locale) {
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
	/**
	 * 
	 * @param locale
	 * @return
	 */
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "lang", dataType = "string", paramType = "query", value = "Accept-Language override") // Locale
	})
	
	@RequestMapping(value = "/sync", method = RequestMethod.PUT /* PATCH? */, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addSync(Locale locale) {
		return new ResponseEntity<>("", HttpStatus.CREATED);
	}
}
