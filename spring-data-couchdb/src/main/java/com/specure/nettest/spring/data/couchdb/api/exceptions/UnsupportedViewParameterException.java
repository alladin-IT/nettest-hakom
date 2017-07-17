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

package com.specure.nettest.spring.data.couchdb.api.exceptions;

import com.specure.nettest.spring.data.couchdb.api.ViewParams;

/**
 * This {@link IllegalArgumentException} is thrown when the underlying Couchdb Driver does not supported a value set in
 * {@link ViewParams}.
 * 
 * @author rwitzel
 */
public class UnsupportedViewParameterException extends IllegalArgumentException {

    private static final long serialVersionUID = 580055614837609769L;

    public UnsupportedViewParameterException(String unsupportedViewParameter) {
        super("view parameter " + unsupportedViewParameter + " is not supported");
    }

}
