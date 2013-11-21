package de.ebertp.dai.claml.model;

import java.io.Serializable;

public class ClaMLFragment implements Serializable  {
	
	String type;
	String content;
	
	
	public ClaMLFragment(String type, String content) {
		super();
		this.type = type;
		this.content = content;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	

}
