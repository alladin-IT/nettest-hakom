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

package com.specure.nettest.shared.model.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.specure.nettest.shared.model.Client;
import com.specure.nettest.shared.model.Settings;
import com.specure.nettest.shared.model.qos.QosMeasurementType;

/**
 * 
 * @author Specure GmbH (bp@specure.com)
 *
 */
public class SettingsResponse implements Serializable {

	private static final long serialVersionUID = -4233654404804487533L;

	/**
	 * 
	 */
	@Expose
	private Settings settings;
	
	/**
	 * 
	 */
	@Expose
	private Client client;
	
	/**
	 * 
	 */
	@Expose
	private List<QosMeasurementTypeResponse> qosMeasurementTypes = new ArrayList<>();
	
	/**
	 * 
	 */
	public SettingsResponse() {
		
	}
	
	/**
	 * 
	 * @return
	 */
	public Settings getSettings() {
		return settings;
	}
	
	/**
	 * 
	 * @param settings
	 */
	public void setSettings(Settings settings) {
		this.settings = settings;
	}
	
	/**
	 * 
	 * @return
	 */
	public Client getClient() {
		return client;
	}
	
	/**
	 * 
	 * @param client
	 */
	public void setClient(Client client) {
		this.client = client;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<QosMeasurementTypeResponse> getQosMeasurementTypes() {
		return qosMeasurementTypes;
	}
	
	/**
	 * 
	 * @param qosMeasurementTypes
	 */
	public void setQosMeasurementTypes(List<QosMeasurementTypeResponse> qosMeasurementTypes) {
		this.qosMeasurementTypes = qosMeasurementTypes;
	}

	/**
	 * 
	 * @author Specure GmbH (bp@specure.com)
	 *
	 */
	public static class QosMeasurementTypeResponse {

		/**
		 * 
		 */
		@SerializedName("test_type")
		@Expose
		private QosMeasurementType type;
	
		/**
		 * 
		 */
		@Expose
		private String name;

		/**
		 * 
		 */
		@Expose
		@SerializedName("desc")
		private String description;
		
		/**
		 * 
		 */
		public QosMeasurementTypeResponse() {
			
		}
		
		/**
		 * 
		 * @param name
		 * @param type
		 */
		public QosMeasurementTypeResponse(QosMeasurementType type, String name, String description) {
			this.type = type;
			this.name = name;
			this.description = description;
		}
		
		/**
		 * 
		 * @return
		 */
		public QosMeasurementType getType() {
			return type;
		}
		
		/**
		 * 
		 * @param type
		 */
		public void setType(QosMeasurementType type) {
			this.type = type;
		}
		
		/**
		 * 
		 * @return
		 */
		public String getName() {
			return name;
		}

		/**
		 * 
		 * @param name
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		/**
		 * 
		 * @return
		 */
		public String getDescription() {
			return description;
		}
		
		/**
		 * 
		 * @param description
		 */
		public void setDescription(String description) {
			this.description = description;
		}
	}
}
