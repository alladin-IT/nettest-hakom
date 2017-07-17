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

package com.specure.nettest.shared;

import com.google.gson.annotations.Expose;

/**
 * 
 * @author Specure GmbH (bp@specure.com)
 *
 */
public abstract class UuidAwareEntity extends CouchDbEntity {

	private static final long serialVersionUID = 996938026473207462L;

	/**
	 * 
	 */
	@Expose
    protected String uuid;
	
	/**
	 * 
	 */
	public UuidAwareEntity() {
		
	}
	
	/**
	 * 
	 * @return
	 */
    public String getUuid() {
    	return uuid;
    }
    
    /**
     * 
     * @param uuid
     */
    public void setUuid(String uuid) {
    	this.uuid = uuid;
    }
}
