
package com.altoCloud.domain;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "Weather")
public class Weather implements Serializable{
	@Id
	@GeneratedValue
	@Column(name = "WEATHER_ID")
	private long id;
	@ManyToOne()
	@Cascade(org.hibernate.annotations.CascadeType.ALL)
	//@JoinColumn(name="stn")
	private Station_Details stn_id;
	private Timestamp date;
	@Column(nullable = true)
	private double TMPF;
	@Column(nullable = true)
	private double SKNT;
	@Column(nullable = true)
	private double DRCT;
	@Column(nullable = true)
	private double GUST;
	@Column(nullable = true)
	private double PMSL;
	@Column(nullable = true)
	private double ALTI;
	@Column(nullable = true)
	private double DWPF;
	@Column(nullable = true)
	private double RELH;
	@Column(nullable = true)
	private double WTHR;
	@Column(nullable = true)
	private double P24I;

	public Weather(){
		
	}
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	//@ManyToOne(cascade = CascadeType.ALL)
	
	public Station_Details getStn_id() {
		return stn_id;
	}

	public void setStn_id(Station_Details stn) {
		this.stn_id = stn;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}
    
	public double getTMPF() {
		return TMPF;
	}

	public void setTMPF(double tMPF) {
		TMPF = tMPF;
	}

	public double getSKNT() {
		return SKNT;
	}

	public void setSKNT(double sKNT) {
		SKNT = sKNT;
	}

	public double getDRCT() {
		return DRCT;
	}

	public void setDRCT(double dRCT) {
		DRCT = dRCT;
	}

	public double getGUST() {
		return GUST;
	}

	public void setGUST(double gUST) {
		GUST = gUST;
	}

	public double getPMSL() {
		return PMSL;
	}

	public void setPMSL(double pMSL) {
		PMSL = pMSL;
	}

	public double getALTI() {
		return ALTI;
	}

	public void setALTI(double aLTI) {
		ALTI = aLTI;
	}

	public double getDWPF() {
		return DWPF;
	}

	public void setDWPF(double dWPF) {
		DWPF = dWPF;
	}

	public double getRELH() {
		return RELH;
	}

	public void setRELH(double rELH) {
		RELH = rELH;
	}

	public double getWTHR() {
		return WTHR;
	}

	public void setWTHR(double wTHR) {
		WTHR = wTHR;
	}

	public double getP24I() {
		return P24I;
	}

	public void setP24I(double p24i) {
		P24I = p24i;
	}

}
