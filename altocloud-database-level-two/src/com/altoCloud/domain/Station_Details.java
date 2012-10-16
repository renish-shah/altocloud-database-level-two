
package com.altoCloud.domain;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "Station_Details")
@IdClass(StationDetailsKey.class)
public class Station_Details {

//	@Id
//	@GeneratedValue
//	@Column(name = "STATION_ID")
//	private long id;
//	
    @Id
    private String stn;
    @Id
	private String stn_name;

	

	public String getStn() {
		return stn;
	}

	public void setStn(String stn) {
		this.stn = stn;
	}

	public String getStn_name() {
		return stn_name;
	}

	public void setStn_name(String stn_name) {
		this.stn_name = stn_name;
	}

	//private String stn;
	private String secondary_id;
	//@Column(nullable = false)
//	private String stn_name;
	@Column(nullable = true)
	private String state;
	@Column(nullable = true)
	private String country;
	@Column(nullable = true)
	private double lat;
	@Column(nullable = true)
	private double lon;
	@Column(nullable = true)
	private double elev;
	@Column(nullable = true)
	private int mnet;
	private String network_name;
//	@Id
//	@Column(name = "stn", updatable = false, nullable = false)
//	public String getStn() {
//		return stn;
//	}
//
//	public void setStn(String stn) {
//		this.stn = stn;
//	}

//	public String getStn_name() {
//		return stn_name;
//	}
//
//	public void setStn_name(String stn_name) {
//		this.stn_name = stn_name;
//	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public double getElev() {
		return elev;
	}

	public void setElev(double elev) {
		this.elev = elev;
	}

	public int getMnet() {
		return mnet;
	}

	public void setMnet(int mnet) {
		this.mnet = mnet;
	}


	public String getNetwork_name() {
		return network_name;
	}

	public void setNetwork_name(String network_name) {
		this.network_name = network_name;
	}

	public String getSecondary_id() {
		return secondary_id;
	}

	public void setSecondary_id(String secondary_id) {
		this.secondary_id = secondary_id;
	}

}
