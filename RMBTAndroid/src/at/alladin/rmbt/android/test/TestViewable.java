/*******************************************************************************
 * Copyright 2013-2017 alladin-IT GmbH
 * Copyright 2014-2016 SPECURE GmbH
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

package at.alladin.rmbt.android.test;

import android.view.View;

import at.alladin.rmbt.client.helper.TestStatus;

/**
 * Created by lb on 06.06.16.
 */
public interface TestViewable {
    void setSpeedString(String speedString);

    void setSpeedValue(double speedValueRelative);

    double setSpeedProgressValue(double speedValue);

    double setProgressValue(double progressValue);

    void setSignalValue(double relativeSignal);

    void setHeaderString(String headerString);

    void setSubHeaderString(String subHeaderString);

    void setProgressString(String progressString);

    void setTestStatus(TestStatus testStatus);

    void setSignalString(final String signalString);

    void setSignalType(final int signalType);

    void setStatusIconView(final View v);

    void recycle();

    void invalidate();
}
