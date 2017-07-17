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

package com.specure.nettest.shared.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.specure.nettest.shared.CouchDbEntity;
import com.specure.nettest.shared.annotation.ExcludeFromRest;

/**
 * 
 * @author Specure GmbH (bp@specure.com)
 *
 */
public class Settings extends CouchDbEntity {

	private static final long serialVersionUID = 7664396452560805174L;

	/**
	 * 
	 */
	@Expose
	private String controlServerIpv4Host;
	
	/**
	 * 
	 */
	@Expose
	private String controlServerIpv6Host;
	
	/**
	 * 
	 */
	@Expose
	private final Thresholds thresholds = new Thresholds();
	
	/**
	 * 
	 */
	@Expose
	private boolean advertisedSpeedsEnabled;
	
	/**
	 * previous: "control_ipv4_only"
	 * @return
	 */
	public String getControlServerIpv4Host() {
		return controlServerIpv4Host;
	}
	
	/**
	 * previous: "control_ipv4_only"
	 * @param controlServerIpv4Host
	 */
	public void setControlServerIpv4Host(String controlServerIpv4Host) {
		this.controlServerIpv4Host = controlServerIpv4Host;
	}
	
	/**
	 * previous: "control_ipv6_only"
	 * @return
	 */
	public String getControlServerIpv6Host() {
		return controlServerIpv6Host;
	}
	
	/**
	 * previous: "control_ipv6_only"
	 * @param controlServerIpv6Host
	 */
	public void setControlServerIpv6Host(String controlServerIpv6Host) {
		this.controlServerIpv6Host = controlServerIpv6Host;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isAdvertisedSpeedsEnabled() {
		return advertisedSpeedsEnabled;
	}

	/**
	 * 
	 * @param advertisedSpeedsEnabled
	 */
	public void setAdvertisedSpeedsEnabled(boolean advertisedSpeedsEnabled) {
		this.advertisedSpeedsEnabled = advertisedSpeedsEnabled;
	}
	
	/**
	 * 
	 * @return
	 */
	public Thresholds getThresholds() {
		return thresholds;
	}
	
	///

	/**
	 * 
	 */
	@Expose
	private final RmbtSettings rmbt = new RmbtSettings();
	
	/**
	 * 
	 */
	@Expose
	private final UrlSettings urls = new UrlSettings();
	
	/**
	 * 
	 */
	@Expose
	private final ServerSettings mapServer = new ServerSettings();
	
	/**
	 * 
	 */
	@Expose
	private final ServerSettings statisticServer = new ServerSettings();

	/**
	 * 
	 */
	@Expose
	private final VersionSettings versions = new VersionSettings();
	
	/**
	 * 
	 */
	@Expose
	private final List<AdvertisedSpeed> advertisedSpeeds = new ArrayList<>();
	
	/**
	 * 
	 */
	@ExcludeFromRest
	@Expose
	private final List<SpeedtestDetailGroup> speedtestDetailGroups = new ArrayList<>();

	/**
	 * 
	 * @return
	 */
	public RmbtSettings getRmbt() {
		return rmbt;
	}
	
	/**
	 * 
	 * @return
	 */
	public UrlSettings getUrls() {
		return urls;
	}

	/**
	 * 
	 * @return
	 */
	public VersionSettings getVersions() {
		return versions;
	}

	/**
	 * 
	 * @return
	 */
	public ServerSettings getMapServer() {
		return mapServer;
	}
	
	/**
	 * 
	 * @return
	 */
	public ServerSettings getStatisticServer() {
		return statisticServer;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<AdvertisedSpeed> getAdvertisedSpeeds() {
		return advertisedSpeeds;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<SpeedtestDetailGroup> getSpeedtestDetailGroups() {
		return speedtestDetailGroups;
	}
	
	////
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Settings [controlServerIpv4Host=" + controlServerIpv4Host + ", controlServerIpv6Host="
				+ controlServerIpv6Host + ", thresholds=" + thresholds + ", advertisedSpeedsEnabled="
				+ advertisedSpeedsEnabled + ", rmbt=" + rmbt + ", urls=" + urls + ", mapServer=" + mapServer
				+ ", statisticServer=" + statisticServer + ", versions=" + versions + ", advertisedSpeeds="
				+ advertisedSpeeds + ", speedtestDetailGroups=" + speedtestDetailGroups + "]";
	}
	
	/**
	 * 
	 * @author Specure GmbH (bp@specure.com)
	 *
	 */
	public static class RmbtSettings {
		
		/**
		 * 
		 */
		@Expose
		private Integer duration;
		
		/**
		 * 
		 */
		@Expose
		private Integer numThreads;
		
		/**
		 * 
		 */
		@Expose
		private Integer numPings;
		
		/**
		 * 
		 */
		@Expose
		private int geoAccuracyButtonLimit;
		
		/**
		 * 
		 */
		@Expose
		private int geoAccuracyDetailLimit;
		
		/**
		 * 
		 */
		@Expose
		private int geoDistanceDetailLimit;
		
		/**
		 * 
		 * @return
		 */
		public Integer getDuration() {
			return duration;
		}
		
		/**
		 * 
		 * @param duration
		 */
		public void setDuration(Integer duration) {
			this.duration = duration;
		}
		
		/**
		 * 
		 * @return
		 */
		public Integer getNumThreads() {
			return numThreads;
		}
		
		/**
		 * 
		 * @param numThreads
		 */
		public void setNumThreads(Integer numThreads) {
			this.numThreads = numThreads;
		}
		
		/**
		 * 
		 * @return
		 */
		public Integer getNumPings() {
			return numPings;
		}
		
		/**
		 * 
		 * @param numPings
		 */
		public void setNumPings(Integer numPings) {
			this.numPings = numPings;
		}
		
		/**
		 * 
		 * @return
		 */
		public int getGeoAccuracyButtonLimit() {
			return geoAccuracyButtonLimit;
		}
	
		/**
		 * 
		 * @param geoAccuracyButtonLimit
		 */
		public void setGeoAccuracyButtonLimit(int geoAccuracyButtonLimit) {
			this.geoAccuracyButtonLimit = geoAccuracyButtonLimit;
		}
		
		/**
		 * 
		 * @return
		 */
		public int getGeoAccuracyDetailLimit() {
			return geoAccuracyDetailLimit;
		}
		
		/**
		 * 
		 * @param geoAccuracyDetailLimit
		 */
		public void setGeoAccuracyDetailLimit(int geoAccuracyDetailLimit) {
			this.geoAccuracyDetailLimit = geoAccuracyDetailLimit;
		}
		
		/**
		 * 
		 * @return
		 */
		public int getGeoDistanceDetailLimit() {
			return geoDistanceDetailLimit;
		}
		
		/**
		 * 
		 * @param geoDistanceDetailLimit
		 */
		public void setGeoDistanceDetailLimit(int geoDistanceDetailLimit) {
			this.geoDistanceDetailLimit = geoDistanceDetailLimit;
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "RmbtSettings [duration=" + duration + ", numThreads=" + numThreads + ", numPings=" + numPings
					+ ", geoAccuracyButtonLimit=" + geoAccuracyButtonLimit + ", geoAccuracyDetailLimit="
					+ geoAccuracyDetailLimit + ", geoDistanceDetailLimit=" + geoDistanceDetailLimit + "]";
		}
	}
	
	/**
	 * 
	 * @author lb@specure.com
	 *
	 */
	public static class VersionSettings {
		
		@Expose
		private String controlServerVersion;

		public String getControlServerVersion() {
			return controlServerVersion;
		}

		public void setControlServerVersion(String controlServerVersion) {
			this.controlServerVersion = controlServerVersion;
		}
	}
	
	/**
	 * 
	 * @author Specure GmbH (bp@specure.com)
	 *
	 */
	public static class UrlSettings {
		
		/**
		 * 
		 */
		@Expose
		private String ipv4IpCheck;
		
		/**
		 * 
		 */
		@Expose
		private String ipv6IpCheck;
		
		/**
		 * 
		 */
		@Expose
		private String statistics;
		
		/**
		 * 
		 */
		@Expose
		private String opendataPrefix;
		
		/**
		 * 
		 * @return
		 */
		public String getIpv4IpCheck() {
			return ipv4IpCheck;
		}
		
		/**
		 * 
		 * @param ipv4IpCheck
		 */
		public void setIpv4IpCheck(String ipv4IpCheck) {
			this.ipv4IpCheck = ipv4IpCheck;
		}
		
		/**
		 * 
		 * @return
		 */
		public String getIpv6IpCheck() {
			return ipv6IpCheck;
		}
		
		/**
		 * 
		 * @param ipv6IpCheck
		 */
		public void setIpv6IpCheck(String ipv6IpCheck) {
			this.ipv6IpCheck = ipv6IpCheck;
		}
		
		/**
		 * 
		 * @return
		 */
		public String getStatistics() {
			return statistics;
		}
		
		/**
		 * 
		 * @param statistics
		 */
		public void setStatistics(String statistics) {
			this.statistics = statistics;
		}
		
		/**
		 * 
		 * @return
		 */
		public String getOpendataPrefix() {
			return opendataPrefix;
		}
		
		/**
		 * 
		 * @param opendataPrefix
		 */
		public void setOpendataPrefix(String opendataPrefix) {
			this.opendataPrefix = opendataPrefix;
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "UrlSettings [ipv4IpCheck=" + ipv4IpCheck + ", ipv6IpCheck=" + ipv6IpCheck + ", statistics="
					+ statistics + ", opendataPrefix=" + opendataPrefix + "]";
		}
	}
	
	/**
	 * 
	 * @author Specure GmbH (bp@specure.com)
	 *
	 */
	public static class ServerSettings {
		
		/**
		 * 
		 */
		@Expose
		private String host;
		
		/**
		 * 
		 */
		@Expose
		private Integer port;
		
		/**
		 * 
		 */
		@Expose
		private Boolean useSsl;
		
		/**
		 * 
		 * @return
		 */
		public String getHost() {
			return host;
		}
		
		/**
		 * 
		 * @param host
		 */
		public void setHost(String host) {
			this.host = host;
		}
		
		/**
		 * 
		 * @return
		 */
		public Integer getPort() {
			return port;
		}
		
		/**
		 * 
		 * @param port
		 */
		public void setPort(Integer port) {
			this.port = port;
		}
		
		/**
		 * 
		 * @return
		 */
		public Boolean getUseSsl() {
			return useSsl;
		}
		
		/**
		 * 
		 * @param useSsl
		 */
		public void setUseSsl(Boolean useSsl) {
			this.useSsl = useSsl;
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "MapServerSettings [host=" + host + ", port=" + port + ", useSsl=" + useSsl + "]";
		}
	}
	
	/**
	 * 
	 * @author Specure GmbH (bp@specure.com)
	 *
	 */
	public static class AdvertisedSpeed {
		
		/**
		 * 
		 */
		@Expose
		private String name;
		
		/**
		 * 
		 */
		@Expose
		private Long minDownloadKbps;
		
		/**
		 * 
		 */
		@Expose
		private Long maxDownloadKbps;
		
		/**
		 * 
		 */
		@Expose
		private Long minUploadKbps;
		
		/**
		 * 
		 */
		@Expose
		private Long maxUploadKbps;
		
		/**
		 * 
		 */
		@Expose
		private boolean enabled;

		/**
		 * 
		 * @return
		 */
		public String getName() {
			return name;
		}

		/**
		 * 
		 * @param name
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * 
		 * @return
		 */
		public Long getMinDownloadKbps() {
			return minDownloadKbps;
		}

		/**
		 * 
		 * @param minDownloadKbps
		 */
		public void setMinDownloadKbps(Long minDownloadKbps) {
			this.minDownloadKbps = minDownloadKbps;
		}

		/**
		 * 
		 * @return
		 */
		public Long getMaxDownloadKbps() {
			return maxDownloadKbps;
		}

		/**
		 * 
		 * @param maxDownloadKbps
		 */
		public void setMaxDownloadKbps(Long maxDownloadKbps) {
			this.maxDownloadKbps = maxDownloadKbps;
		}

		/**
		 * 
		 * @return
		 */
		public Long getMinUploadKbps() {
			return minUploadKbps;
		}

		/**
		 * 
		 * @param minUploadKbps
		 */
		public void setMinUploadKbps(Long minUploadKbps) {
			this.minUploadKbps = minUploadKbps;
		}

		/**
		 * 
		 * @return
		 */
		public Long getMaxUploadKbps() {
			return maxUploadKbps;
		}

		/**
		 * 
		 * @param maxUploadKbps
		 */
		public void setMaxUploadKbps(Long maxUploadKbps) {
			this.maxUploadKbps = maxUploadKbps;
		}

		/**
		 * 
		 * @return
		 */
		public boolean isEnabled() {
			return enabled;
		}

		/**
		 * 
		 * @param enabled
		 */
		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "AdvertisedSpeed [name=" + name + ", minDownloadKbps=" + minDownloadKbps + ", maxDownloadKbps="
					+ maxDownloadKbps + ", minUploadKbps=" + minUploadKbps + ", maxUploadKbps=" + maxUploadKbps
					+ ", enabled=" + enabled + "]";
		}
	}
	
	/**
	 * 
	 * @author alladin-IT GmbH (bp@alladin.at)
	 *
	 */
	public static class Thresholds {
		
		/**
		 * 
		 */
		@Expose
		private String download;
		
		/**
		 * 
		 */
		@Expose
		private String upload;
		
		/**
		 * 
		 */
		@Expose
		private String ping;
		
		/**
		 * 
		 */
		@Expose
		private String signalMobile;
		
		/**
		 * 
		 */
		@Expose
		private String signalRsrp;
		
		/**
		 * 
		 */
		@Expose
		private String signalWifi;

		/**
		 * 
		 * @return
		 */
		public String getDownload() {
			return download;
		}

		/**
		 * 
		 * @param download
		 */
		public void setDownload(String download) {
			this.download = download;
		}

		/**
		 * 
		 * @return
		 */
		public String getUpload() {
			return upload;
		}

		/**
		 * 
		 * @param upload
		 */
		public void setUpload(String upload) {
			this.upload = upload;
		}

		/**
		 * 
		 * @return
		 */
		public String getPing() {
			return ping;
		}

		/**
		 * 
		 * @param ping
		 */
		public void setPing(String ping) {
			this.ping = ping;
		}

		/**
		 * 
		 * @return
		 */
		public String getSignalMobile() {
			return signalMobile;
		}

		/**
		 * 
		 * @param signalMobile
		 */
		public void setSignalMobile(String signalMobile) {
			this.signalMobile = signalMobile;
		}

		/**
		 * 
		 * @return
		 */
		public String getSignalRsrp() {
			return signalRsrp;
		}

		/**
		 * 
		 * @param signalRsrp
		 */
		public void setSignalRsrp(String signalRsrp) {
			this.signalRsrp = signalRsrp;
		}

		/**
		 * 
		 * @return
		 */
		public String getSignalWifi() {
			return signalWifi;
		}

		/**
		 * 
		 * @param signalWifi
		 */
		public void setSignalWifi(String signalWifi) {
			this.signalWifi = signalWifi;
		}
	}
	
	public static class SpeedtestDetailGroup {

		/**
		 * The key associated w/the detail group
		 * Is the same as the translation key for the given detail group
		 */
		@Expose
		private String key;
		
		/**
		 * The icon of the detail group
		 */
		@Expose
		private String icon;
		
		/**
		 * The single entries of the group (e.g. open_test_uuid, device_info.model, ...)
		 */
		@Expose
		private List<SpeedtestDetailGroupEntry> values = new ArrayList<>();

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getIcon() {
			return icon;
		}

		public void setIcon(String icon) {
			this.icon = icon;
		}
		
		public List<SpeedtestDetailGroupEntry> getValues() {
			return values;
		}

		public void setValues(List<SpeedtestDetailGroupEntry> values) {
			this.values = values;
		}
		
		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "SpeedtestDetailGroup [key=" + key + ", icon=" + icon + ", values=" + values + "]";
		}
		
		public static class SpeedtestDetailGroupEntry {

			/**
			 * The key of an entry in the values of a speedtestDetailGroup
			 */
			@Expose
			private String key;
			
			/**
			 * Key to find the translated title
			 */
			@SerializedName("translation_key")
			@Expose
			private String translationKey;
			
			/**
			 * The unit to be appended to the value, optional
			 */
			@SerializedName("unit")
			@Expose
			private String unit;
			
			/**
			 * Defines the format to be applied to the value of the given key, optional
			 */
			@SerializedName("format")
			@Expose
			private FormatEnum format;

			@SerializedName("value")
			@Expose
			private String value;
			
			public String getTranslationKey() {
				return translationKey;
			}

			public void setTranslationKey(String translationKey) {
				this.translationKey = translationKey;
			}

			public String getKey() {
				return key;
			}
			
			public void setKey(String key) {
				this.key = key;
			}
			
			public String getUnit() {
				return unit;
			}

			public void setUnit(String unit) {
				this.unit = unit;
			}

			public FormatEnum getFormat() {
				return format;
			}

			public void setFormat(FormatEnum format) {
				this.format = format;
			}

			public String getValue() {
				return value;
			}

			public void setValue(String value) {
				this.value = value;
			}
			
			@Override
			public String toString() {
				return "SpeedtestDetailGroupEntry [key=" + key + ", translationKey=" + translationKey + ", unit=" + unit
						+ ", format=" + format + ", value=" + value + "]";
			}
			/**
			 * Enumeration to define possible formattings for a SpeedtestDetailGroupEntry
			 * TODO: rename formatEnums in a smart way
			 */
			public enum FormatEnum{
				DIV_1000(1000d),	// => / 1000d
				DIV_1000000(1000000d),	// => / 1000000d
				DIV_1000000000(1000000000d),	// => / 1000000000d
				DIV_1000000000000(1000000000000d);	// => / 1000000000000d
				
				private double divider;
				
				FormatEnum(double divider) {
					this.divider = divider;
				}
				
				public double getDivider() {
					return divider;
				}
			}
		}
	}
}
