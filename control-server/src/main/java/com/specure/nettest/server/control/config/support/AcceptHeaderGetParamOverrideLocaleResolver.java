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

package com.specure.nettest.server.control.config.support;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

/**
 * 
 * @author Specure GmbH (bp@specure.com)
 *
 */
public class AcceptHeaderGetParamOverrideLocaleResolver extends AcceptHeaderLocaleResolver {
	
	@SuppressWarnings("unused")
	private final Logger logger = LoggerFactory.getLogger(AcceptHeaderGetParamOverrideLocaleResolver.class);
	
	/**
	 * 
	 */
	private static final String LANGUAGE_PARAMETER = "lang";
	
	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver#resolveLocale(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		final String langParam = request.getParameter(LANGUAGE_PARAMETER);
		
		if (StringUtils.hasText(langParam)) {
			final Locale locale = StringUtils.parseLocaleString(langParam); // TODO: check if locale is valid
			return locale;
		}
		
		return super.resolveLocale(request);
	}
}
