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

import java.util.Locale;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.specure.nettest.server.control.config.ControlServerProperties;
import com.specure.nettest.shared.model.Settings;

@Service
public class ClassificationService {

	@Inject
	private SettingsService settingsService;

	
	@Inject
	private ControlServerProperties controlServerProperties;
	
//	@PostConstruct
	public void init() {
        int[] downloadValues = null;
        int[] uploadValues = null;
        int[] pingValues = null;
        int[] signalMobileValues = null;
        int[] signalRsrpValues = null;
        int[] signalWifiValues = null;
        
        final Settings settings = settingsService.getSettings(controlServerProperties.getSettingsKey());
        
        try { uploadValues = getIntValues(settings.getThresholds().getUpload(), 2); } catch (Exception e) { e.printStackTrace(); }
        try { downloadValues = getIntValues(settings.getThresholds().getDownload(), 2); } catch (Exception e) { e.printStackTrace(); }
        try { pingValues = getIntValues(settings.getThresholds().getPing(), 2); } catch (Exception e) { e.printStackTrace(); }
        try { signalMobileValues = getIntValues(settings.getThresholds().getSignalMobile(), 2); } catch (Exception e) { e.printStackTrace(); }
        try { signalRsrpValues = getIntValues(settings.getThresholds().getSignalRsrp(), 2); } catch (Exception e) { e.printStackTrace(); }
        try { signalWifiValues = getIntValues(settings.getThresholds().getSignalWifi(), 2); } catch (Exception e) { e.printStackTrace(); }
        
        if (uploadValues == null) {
            uploadValues = new int[] { 1000, 500 }; // default
        }
        THRESHOLD_UPLOAD = uploadValues;
//        THRESHOLD_UPLOAD_CAPTIONS = getCaptions(uploadValues);
        
        if (downloadValues == null) {
            downloadValues = new int[] { 2000, 1000 }; // default
        }
        THRESHOLD_DOWNLOAD = downloadValues;
//        THRESHOLD_DOWNLOAD_CAPTIONS = getCaptions(downloadValues);
        
        if (pingValues == null) {
        	pingValues = new int[] { 25, 75 }; // default
        }
        THRESHOLD_PING = pingValues;
//        THRESHOLD_PING_CAPTIONS = getCaptions(pingValues);
        
        if (pingValues.length == 2) {
        	THRESHOLD_PING[0] *= 1000000;
        	THRESHOLD_PING[1] *= 1000000;
        }
        
        if (signalMobileValues == null) {
        	signalMobileValues = new int[] { -85, -101 }; // default
        }
        THRESHOLD_SIGNAL_MOBILE = signalMobileValues;
//        THRESHOLD_SIGNAL_MOBILE_CAPTIONS = getCaptions(signalMobileValues);
        
        if (signalRsrpValues == null) {
        	signalRsrpValues = new int[] { -95, -111 }; // default
        }
        THRESHOLD_SIGNAL_RSRP = signalRsrpValues;
//        THRESHOLD_SIGNAL_RSRP_CAPTIONS = getCaptions(signalRsrpValues);
        
        if (signalWifiValues == null) {
        	signalWifiValues = new int[] { -61, -76 }; // default
        }
        THRESHOLD_SIGNAL_WIFI = signalWifiValues;
//        THRESHOLD_SIGNAL_WIFI_CAPTIONS = getCaptions(signalWifiValues);
    }

//    private static String[] getCaptions(int[] values)
//    {
//        final String[] result = new String[values.length];
//        for (int i = 0; i < values.length; i++)
//            result[i] = String.format(Locale.US, "%.1f", ((double)values[i]) / 1000);
//        return result;
//    }

    private static int[] getIntValues(String value, int expectCount) {
    	if (value == null) {
    		return null;
    	}
    	
    	final String parts[] = value.split(";");
        if (parts.length != expectCount) {
            throw new IllegalArgumentException(String.format(Locale.US, "unexpected number of parameters (expected %d): \"%s\"", expectCount, value));
        }
        
        final int[] result = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            result[i] = Integer.parseInt(parts[i]);
        }
        
        return result;
    }
    
    private int[] THRESHOLD_UPLOAD = null;
//    private String[] THRESHOLD_UPLOAD_CAPTIONS;
    
    private int[] THRESHOLD_DOWNLOAD = null;
//    private String[] THRESHOLD_DOWNLOAD_CAPTIONS;
    
    private int[] THRESHOLD_PING;
//    private String[] THRESHOLD_PING_CAPTIONS;
    
    // RSSI limits used for 2G,3G (and 4G when RSSI is used)
    // only odd values are reported by 2G/3G 
    private int[] THRESHOLD_SIGNAL_MOBILE; // -85 is still green, -101 is still yellow
//    private String[] THRESHOLD_SIGNAL_MOBILE_CAPTIONS;
    
    // RSRP limit used for 4G
    private int[] THRESHOLD_SIGNAL_RSRP;
//    private String[] THRESHOLD_SIGNAL_RSRP_CAPTIONS;

    // RSSI limits used for Wifi
    private int[] THRESHOLD_SIGNAL_WIFI;
//    private String[] THRESHOLD_SIGNAL_WIFI_CAPTIONS;
    
    public int classify(final int[] threshold, final long value)
    {
        final boolean inverse = threshold[0] < threshold[1];
        
        if (!inverse)
        {
            if (value >= threshold[0])
                return 3; // GREEN
            else if (value >= threshold[1])
                return 2; // YELLOW
            else
                return 1; // RED
        }
        else if (value <= threshold[0])
            return 3;
        else if (value <= threshold[1])
            return 2;
        else
            return 1;
    }

    public int classify(final ClassificationType type, final long value) {
    	if (THRESHOLD_DOWNLOAD == null || THRESHOLD_UPLOAD == null) {
    		//lazy initialization
    		init();
    	}
    	
    	switch (type) {
    	case DOWNLOAD:
    		return classify(THRESHOLD_DOWNLOAD, value);
    	case UPLOAD:
    		return classify(THRESHOLD_UPLOAD, value);
    	case PING:
    		return classify(THRESHOLD_PING, value);
    	case SIGNAL_MOBILE:
    		return classify(THRESHOLD_SIGNAL_MOBILE, value);
    	case SIGNAL_RSRP:
    		return classify(THRESHOLD_SIGNAL_RSRP, value);
    	case SIGNAL_WIFI:
    		return classify(THRESHOLD_SIGNAL_WIFI, value);
    	}
    	
    	return 1;
    }

    public static enum ClassificationType {
    	UPLOAD,
    	DOWNLOAD,
    	PING,
    	SIGNAL_MOBILE,
    	SIGNAL_WIFI,
    	SIGNAL_RSRP
    }
}
