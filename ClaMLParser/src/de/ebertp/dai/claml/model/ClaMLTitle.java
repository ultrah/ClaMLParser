package de.ebertp.dai.claml.model;

import java.io.Serializable;

public class ClaMLTitle implements Serializable {
	
	String date;
	String name;
	String version;
	
	
	public ClaMLTitle(String date, String name, String version) {
		super();
		this.date = date;
		this.name = name;
		this.version = version;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}
	
	
	
	

}
