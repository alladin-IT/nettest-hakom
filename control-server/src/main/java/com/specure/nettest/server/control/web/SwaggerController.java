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

package com.specure.nettest.server.control.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.specure.nettest.server.control.config.Constants;

/**
 * 
 * @author Specure GmbH (bp@specure.com)
 *
 */
@Controller
@Profile("!" + Constants.SPRING_PROFILE_PRODUCTION) // exclude in production
public class SwaggerController {

	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(SwaggerController.class);
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping("/swagger")
	public String redirectToSwaggerUi() {
		return "redirect:swagger-ui.html";
	}
}
