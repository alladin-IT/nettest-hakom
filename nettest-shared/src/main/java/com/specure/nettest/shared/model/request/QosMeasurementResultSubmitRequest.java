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

package com.specure.nettest.shared.model.request;

import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author lb@specure.com
 *
 */
public class QosMeasurementResultSubmitRequest extends BasicRequestImpl {

    /**
     * 
     */
    @SerializedName("qos_result")
    @Expose
    private List<JsonObject> qosResultList;

    /**
     * 
     */
    public QosMeasurementResultSubmitRequest() {
    	
    }
    
    /**
     * 
     * @return
     */
    public List<JsonObject> getQosResultList() {
		return qosResultList;
	}
    
    /**
     * 
     * @param qosResultList
     */
    public void setQosResultList(List<JsonObject> qosResultList) {
		this.qosResultList = qosResultList;
	}
}