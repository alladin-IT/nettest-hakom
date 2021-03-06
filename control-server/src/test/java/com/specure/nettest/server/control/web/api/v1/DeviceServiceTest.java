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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Test;

import com.specure.nettest.server.control.service.DeviceService;
import com.specure.nettest.shared.model.Device;

public class DeviceServiceTest extends AbstractTest {

	@Inject
	private DeviceService deviceService;
	
	@Test
	public void test() {
		Device device = deviceService.findByCodename("i386");
		
		assertNotNull(device);
		assertEquals("i386", device.getCodename());
	}
}
