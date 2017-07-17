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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.stereotype.Service;

import com.specure.nettest.shared.model.GeoLocation;
import com.specure.nettest.shared.model.Measurement;

@Service
public class GeoLocationService {

	/**
	 * previous version see: OpenTestResource.LocationGraph
	 * @param measurement
	 * @param testStartMs
	 * @return
	 */
	public LocationGraph getLocationGraph(final Measurement measurement, final long testStartMs) {
		if (measurement.getLocations() == null || measurement.getLocations().isEmpty()) {
			return null;
		}
		
		return new LocationGraph(measurement, testStartMs);
	}
	
    public static class LocationGraph {
    	private double totalDistance;
    	private ArrayList<LocationGraphItem> locations = new ArrayList<>();
    	
    	/**
    	 * Gets all distinctive locations of a client during a test
    	 * @param testUID the uid of the test
    	 * @param testTime the begin of the test
    	 * @throws SQLException
    	 */
    	public LocationGraph(final Measurement measurement, final long testStartMs) {
            boolean first = true;
            boolean usedCurrentItem = false;
            LocationGraphItem item = null;
            
            double lastLat=0;
            double lastLong=0;
            double lastAcc=0;
            this.totalDistance=0;
            final Iterator<GeoLocation> locationIterator = measurement.getLocations().iterator();
            while (locationIterator.hasNext()) {
            	final GeoLocation rsLocation = locationIterator.next();
            	
            	// skip invalid geo locations
            	if (rsLocation.getTime() == null) {
            		continue;
            	}
            	
            	//long timeElapsed = rsLocation.getTimestamp("time").getTime() - testTime;
            	long timeElapsed = rsLocation.getTime().getMillis() - testStartMs;
            	//there could be measurements taken before a test started
            	//in this case, only return the last one
            	if (first && timeElapsed > 0 && item != null) {
            		this.locations.add(item);
            		lastLat = item.getLatitude();
    				lastLong = item.getLongitude();
    				lastAcc = item.getAccuracy();
            		first = false;
            	}
            	
            	
            	item = new LocationGraphItem(Math.max(timeElapsed,0), rsLocation.getLongitude(), rsLocation.getLatitude(), 
            			rsLocation.getAccuracy() != null ? rsLocation.getAccuracy() : 0d);
            	
            	usedCurrentItem = false;
            	
            	//put triplet in the array if it is not the first one
            	if (!first) {
            		//only put the point in the resulting array, if there is a significant
            		//distance from the last point
            		//therefore (difference in m) > (tolerance last point + tolerance new point)
            		double diff = GeoLocationService.distFrom(lastLat, lastLong, item.getLatitude(), item.getLongitude());
            		//System.out.println("dist: " + diff);
            		double maxDiff = item.getAccuracy() + lastAcc;
            		//System.out.println("Distance: " + diff + "; tolTotal " + maxDiff + "; tol1 " + lastAcc + "; tol2 " + json.getDouble("loc_accuracy"));
            		if (diff > maxDiff) {
            			this.locations.add(item);
                		lastLat = item.getLatitude();
        				lastLong = item.getLongitude();
        				lastAcc = item.getAccuracy();
        				this.totalDistance += diff;
            		}
            		else {
            			//if not, replace the old point, if the new is more accurate
            			if (item.getAccuracy() < lastAcc) {
            				this.locations.remove(this.locations.size()-1);
            				this.locations.add(item);
                    		lastLat = item.getLatitude();
            				lastLong = item.getLongitude();
            				lastAcc = item.getAccuracy();
            			}
            		}
            		
            		usedCurrentItem = true;
            	}
            }
            
            //always use the last item to connect the path with the end point
            if (!usedCurrentItem && this.locations.size() > 0) {
            	//replace the last set point with it
            	//since it is in the same inaccurracy area
            	
            	this.locations.remove(this.locations.size()-1);
				this.locations.add(item);
        		lastLat = item.getLatitude();
				lastLong = item.getLongitude();
				lastAcc = item.getAccuracy();
            	
            }            
    	}
    	
    	public double getTotalDistance() {
    		return this.totalDistance;
    	}
    	
    	public ArrayList<LocationGraphItem> getLocations() {
    		return this.locations;
    	}
    	
    	public class LocationGraphItem {
    		private double longitude;
    		private double latitude;
    		private double accuracy;
    		private long timeElapsed;
    		
    		public LocationGraphItem(long timeElapsed, double longitude, double latitude, double accuracy) {
    			this.longitude = longitude;
    			this.latitude = latitude;
    			this.timeElapsed = timeElapsed;
    			this.accuracy = accuracy;
    		}
    		
    		/**
    		 * @return The time elapsed since the begin of the test
    		 */
    		public long getTimeElapsed() {
    			return this.timeElapsed;
    		}
    		
    		public double getLongitude() {
    			return this.longitude;
    		}
    		
    		public double getLatitude() {
    			return this.latitude;
    		}
    		
    		/**
    		 * @return The accuracy of the measurement in meters
    		 */
    		public double getAccuracy() {
    			return this.accuracy;
    		}
    	}
    }
    
    /**
     * Calculate the rough distance in meters between two points
     * taken from http://stackoverflow.com/questions/120283/working-with-latitude-longitude-values-in-java
     * @param lat1 
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    private static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius =  6371000;
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        return dist;
    }
}
