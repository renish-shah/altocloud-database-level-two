package com.altoCloud.domain;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.sun.org.apache.xpath.internal.operations.Equals;

//@Embeddable
public class Station_details_key implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String stn;
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
	

    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (obj == this)
            return true;
        if (obj.getClass() != getClass())
            return false;

        Station_details_key rhs = (Station_details_key) obj;
       if((rhs.stn==this.stn)&&(rhs.stn_name==this.stn_name))
          return true;
       else
        	  return false;
    
		
	}

}
