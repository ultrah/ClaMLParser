package de.ebertp.dai.claml.model;

import java.io.Serializable;

public class ClaMLIdentifier implements Serializable {
	
	String authority;
	String uid;
	
	public ClaMLIdentifier(String authority, String uid) {
		this.authority = authority;
		this.uid = uid;
	}
	
	/**
	 * @return the authority
	 */
	public String getAuthority() {
		return authority;
	}
	/**
	 * @param authority the authority to set
	 */
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}
	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	

}
