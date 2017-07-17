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

import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.lightcouch.NoDocumentException;

import com.specure.nettest.server.control.exception.measurement.MeasurementServerNotFoundException;
import com.specure.nettest.shared.model.MeasurementServer;
import com.specure.nettest.spring.data.couchdb.repository.CouchDbRepository;


/**
 * 
 * @author lb@specure.com
 *
 */
@Service
public class MeasurementServerService {

	/**
	 * 
	 */
	@Inject
	private CouchDbRepository<MeasurementServer> measurementServerRepository;
	
	/**
	 * 
	 * @param serverType
	 * @return
	 */
	public MeasurementServer getNearestMeasurementServer(MeasurementServer.Type serverType) {
		final List<MeasurementServer> measurementServerList;
		
		try {
			measurementServerList = measurementServerRepository.getView("nearest_server")
					.reduce(false)
					//.startKey(serverType.toString(), "\ufff0")
					//.endKey(serverType.toString(), null)
					.startKey(serverType.toString(), null)
					.endKey(serverType.toString(), "\ufff0")
					.includeDocs(true)
					//.descending(true)
					.limit(10) // TODO: limit?
					.query(MeasurementServer.class);
			
			if (measurementServerList.isEmpty()) {
				throw new MeasurementServerNotFoundException();
			}
		} catch (NoDocumentException e) {
			// View not found
			throw new MeasurementServerNotFoundException();
		}
		
		// TODO: Select nearest
		
		// randomize
		return measurementServerList.get(new Random().nextInt(measurementServerList.size()));
	}
	
	/**
	 * 
	 * @return
	 */
	public MeasurementServer getNearestSpeedMeasurementServer() {
		return this.getNearestMeasurementServer(MeasurementServer.Type.SPEED_TCP);
	}
	
	/**
	 * 
	 * @return
	 */
	public MeasurementServer getNearestSpeedWebsocketMeasurementServer() {
		return this.getNearestMeasurementServer(MeasurementServer.Type.SPEED_WEBSOCKET);
	}
	
	/**
	 * 
	 * @return
	 */
	public MeasurementServer getNearestQosMeasurementServer() {
		return this.getNearestMeasurementServer(MeasurementServer.Type.QOS);
	}
}
