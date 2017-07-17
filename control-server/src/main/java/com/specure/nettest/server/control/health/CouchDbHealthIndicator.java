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

package com.specure.nettest.server.control.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.stereotype.Component;

/**
 * 
 * @author alladin-IT GmbH (bp@alladin.at)
 *
 */
@Component
public class CouchDbHealthIndicator extends AbstractHealthIndicator {

	/*
	 * (non-Javadoc)
	 * @see org.springframework.boot.actuate.health.AbstractHealthIndicator#doHealthCheck(org.springframework.boot.actuate.health.Health.Builder)
	 */
	@Override
	protected void doHealthCheck(Builder builder) throws Exception {
		
		
		builder.down();
		builder.withDetail("test", "test123");
	}
}
