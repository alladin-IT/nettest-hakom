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

package at.alladin.rmbt.shared;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

/**
 * 
 * @author alladin-IT GmbH (?@alladin.at)
 *
 */
public final class Classification {
	
	/**
	 * 
	 */
	private static Classification instance;
	
	public final int[] THRESHOLD_UPLOAD;
    public final String[] THRESHOLD_UPLOAD_CAPTIONS;
    
    public final int[] THRESHOLD_DOWNLOAD;
    public final String[] THRESHOLD_DOWNLOAD_CAPTIONS;
    
    public final int[] THRESHOLD_PING;
    public final String[] THRESHOLD_PING_CAPTIONS;
    
    // RSSI limits used for 2G,3G (and 4G when RSSI is used)
    // only odd values are reported by 2G/3G 
    public final int[] THRESHOLD_SIGNAL_MOBILE; // -85 is still green, -101 is still yellow
    public final String[] THRESHOLD_SIGNAL_MOBILE_CAPTIONS;
    
    // RSRP limit used for 4G
    public final int[] THRESHOLD_SIGNAL_RSRP;
    public final String[] THRESHOLD_SIGNAL_RSRP_CAPTIONS;

    // RSSI limits used for Wifi
    public final int[] THRESHOLD_SIGNAL_WIFI;
    public final String[] THRESHOLD_SIGNAL_WIFI_CAPTIONS;
	
	/**
	 * 
	 * @return
	 */
    public static Classification getInstance() {
        return instance;
    }
    
    /**
     * 
     * @param conn
     */
    public static void initInstance(Connection conn) {
        instance = new Classification(conn);
    }
    
    /**
     * 
     * @param conn
     */
    private Classification(Connection conn) {
        int[] uploadValues = null;
        int[] downloadValues = null;
        int[] pingValues = null;
        int[] signalMobileValues = null;
        int[] signalRsrpValues = null;
        int[] signalWifiValues = null;
        
        final String sql = "SELECT json->'thresholds'->>'download' as threshold_download, "
        		+ "json->'thresholds'->>'upload' as threshold_upload, "
        		+ "json->'thresholds'->>'ping' as threshold_ping, "
        		+ "json->'thresholds'->>'signalMobile' as threshold_signal_mobile, json->'thresholds'->>'signalRsrp' as threshold_signal_rsrp, json->'thresholds'->>'signalWifi' as threshold_signal_wifi "
        		+ "FROM ha_settings LIMIT 1";
        
        try (final PreparedStatement ps = conn.prepareStatement(sql)) {
        	try (final ResultSet rs = ps.executeQuery()) {
        		if (rs.next()) {
        			downloadValues = getIntValues(rs.getString("threshold_download"), 2);
        			uploadValues = getIntValues(rs.getString("threshold_upload"), 2);
        			pingValues = getIntValues(rs.getString("threshold_ping"), 2);
        			signalMobileValues = getIntValues(rs.getString("threshold_signal_mobile"), 2);
        			signalRsrpValues = getIntValues(rs.getString("threshold_signal_rsrp"), 2);
        			signalWifiValues = getIntValues(rs.getString("threshold_signal_wifi"), 2);
        		}
        	}
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        
        if (uploadValues == null) {
            uploadValues = new int[] { 1000, 500 }; // default
        }
        THRESHOLD_UPLOAD = uploadValues;
        THRESHOLD_UPLOAD_CAPTIONS = getCaptions(uploadValues);
        
        if (downloadValues == null) {
            downloadValues = new int[] { 2000, 1000 }; // default
        }
        THRESHOLD_DOWNLOAD = downloadValues;
        THRESHOLD_DOWNLOAD_CAPTIONS = getCaptions(downloadValues);
        
        if (pingValues == null) {
        	pingValues = new int[] { 25, 75 }; // default
        }
        THRESHOLD_PING = pingValues;
        THRESHOLD_PING_CAPTIONS = getCaptions(pingValues);
        
        if (pingValues.length == 2) {
        	THRESHOLD_PING[0] *= 1000000;
        	THRESHOLD_PING[1] *= 1000000;
        }
        
        if (signalMobileValues == null) {
        	signalMobileValues = new int[] { -85, -101 }; // default
        }
        THRESHOLD_SIGNAL_MOBILE = signalMobileValues;
        THRESHOLD_SIGNAL_MOBILE_CAPTIONS = getCaptions(signalMobileValues);
        
        if (signalRsrpValues == null) {
        	signalRsrpValues = new int[] { -95, -111 }; // default
        }
        THRESHOLD_SIGNAL_RSRP = signalRsrpValues;
        THRESHOLD_SIGNAL_RSRP_CAPTIONS = getCaptions(signalRsrpValues);
        
        if (signalWifiValues == null) {
        	signalWifiValues = new int[] { -61, -76 }; // default
        }
        THRESHOLD_SIGNAL_WIFI = signalWifiValues;
        THRESHOLD_SIGNAL_WIFI_CAPTIONS = getCaptions(signalWifiValues);
    }

    /**
     * 
     * @param values
     * @return
     */
    private static String[] getCaptions(int[] values) {
        final String[] result = new String[values.length];
        for (int i = 0; i < values.length; i++)
            result[i] = String.format(Locale.US, "%.1f", ((double)values[i]) / 1000);
        return result;
    }

    /**
     * 
     * @param value
     * @param expectCount
     * @return
     * @throws SQLException
     * @throws NumberFormatException
     * @throws IllegalArgumentException
     */
    private static int[] getIntValues(String value, int expectCount) throws SQLException, NumberFormatException, IllegalArgumentException {
    	if (value == null) {
    		return null;
        }
            
        final String[] parts = value.split(";");
        if (parts.length != expectCount) {
        	throw new IllegalArgumentException(String.format(Locale.US, "unexpected number of parameters (expected %d): \"%s\"", expectCount, value));
        }
        
        final int[] result = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
        	result[i] = Integer.parseInt(parts[i]);
        }
        
        return result;
    }
    
    /**
     * 
     * @param threshold
     * @param value
     * @return
     */
    public static int classify(final int[] threshold, final long value) {
        final boolean inverse = threshold[0] < threshold[1];
        
        if (!inverse) {
            if (value >= threshold[0]) {
                return 3; // GREEN
            } else if (value >= threshold[1]) {
                return 2; // YELLOW
            } else {
                return 1; // RED
            }
        } else if (value <= threshold[0]) {
            return 3;
        } else if (value <= threshold[1]) {
            return 2;
        } else {
            return 1;
        }
    }
}
