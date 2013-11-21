package de.ebertp.dai.claml.model;

public class ClaMLMeta {
	
	String value;
	String name;
	
	public ClaMLMeta(String value, String name) {
		super();
		this.value = value;
		this.name = name;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
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
	
	

}
